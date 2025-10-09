package com.example.moldescmms.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Molde
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import kotlinx.coroutines.launch

class SolicitudMantenimientoFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private val moldes = mutableListOf<Molde>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_form)
        
        database = AppDatabase.getDatabase(this)
        
        setupSpinners()
        setupSubtipoListener()
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveSolicitud()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        // Departamentos ampliados
        val departamentos = arrayOf("Producción", "Calidad", "Mantenimiento Máquinas")
        val deptAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_departamento).adapter = deptAdapter
        
        lifecycleScope.launch {
            moldes.clear()
            moldes.addAll(database.moldeDao().getAllList())
            
            val moldesNames = moldes.map { "${it.codigo} - ${it.nombre}" }
            val moldeAdapter = ArrayAdapter(this@SolicitudMantenimientoFormActivity, 
                android.R.layout.simple_spinner_item, moldesNames)
            moldeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.sp_molde).adapter = moldeAdapter
        }
        
        val tipos = arrayOf("Preventivo", "Correctivo")
        val tipoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_tipo).adapter = tipoAdapter
        
        val subtipos = arrayOf(
            "Pulido",
            "Limpieza",
            "Reparación",
            "Cambio de Conexión Rápida",
            "Cambio de Manguera"
        )
        val subtipoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subtipos)
        subtipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_subtipo).adapter = subtipoAdapter
        
        val detallesConexion = arrayOf("Agua", "Aire", "Ambas")
        val detalleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, detallesConexion)
        detalleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_detalle_conexion).adapter = detalleAdapter
        
        val calibres = arrayOf("8", "10", "12")
        val calibreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, calibres)
        calibreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_calibre).adapter = calibreAdapter
        
        val prioridades = arrayOf("Urgente", "Alta", "Media", "Baja")
        val prioridadAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        prioridadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_prioridad).adapter = prioridadAdapter
        findViewById<Spinner>(R.id.sp_prioridad).setSelection(2)
    }
    
    private fun setupSubtipoListener() {
        findViewById<Spinner>(R.id.sp_subtipo).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val subtipo = parent?.getItemAtPosition(position).toString()
                
                when (subtipo) {
                    "Cambio de Conexión Rápida" -> {
                        findViewById<TextView>(R.id.tv_detalles_conexion).visibility = View.VISIBLE
                        findViewById<Spinner>(R.id.sp_detalle_conexion).visibility = View.VISIBLE
                        findViewById<CheckBox>(R.id.cb_conexion_y).visibility = View.VISIBLE
                        
                        findViewById<TextView>(R.id.tv_calibre).visibility = View.GONE
                        findViewById<Spinner>(R.id.sp_calibre).visibility = View.GONE
                        findViewById<TextView>(R.id.tv_cantidad_mangueras).visibility = View.GONE
                        findViewById<EditText>(R.id.et_cantidad_mangueras).visibility = View.GONE
                    }
                    "Cambio de Manguera" -> {
                        findViewById<TextView>(R.id.tv_detalles_conexion).visibility = View.VISIBLE
                        findViewById<Spinner>(R.id.sp_detalle_conexion).visibility = View.VISIBLE
                        findViewById<TextView>(R.id.tv_calibre).visibility = View.VISIBLE
                        findViewById<Spinner>(R.id.sp_calibre).visibility = View.VISIBLE
                        findViewById<TextView>(R.id.tv_cantidad_mangueras).visibility = View.VISIBLE
                        findViewById<EditText>(R.id.et_cantidad_mangueras).visibility = View.VISIBLE
                        
                        findViewById<CheckBox>(R.id.cb_conexion_y).visibility = View.GONE
                    }
                    else -> {
                        findViewById<TextView>(R.id.tv_detalles_conexion).visibility = View.GONE
                        findViewById<Spinner>(R.id.sp_detalle_conexion).visibility = View.GONE
                        findViewById<CheckBox>(R.id.cb_conexion_y).visibility = View.GONE
                        findViewById<TextView>(R.id.tv_calibre).visibility = View.GONE
                        findViewById<Spinner>(R.id.sp_calibre).visibility = View.GONE
                        findViewById<TextView>(R.id.tv_cantidad_mangueras).visibility = View.GONE
                        findViewById<EditText>(R.id.et_cantidad_mangueras).visibility = View.GONE
                    }
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun saveSolicitud() {
        val spMolde = findViewById<Spinner>(R.id.sp_molde)
        if (moldes.isEmpty() || spMolde.selectedItemPosition < 0) {
            Toast.makeText(this, "Debe seleccionar un molde", Toast.LENGTH_SHORT).show()
            return
        }
        
        val problema = findViewById<EditText>(R.id.et_problema).text.toString()
        if (problema.isEmpty()) {
            Toast.makeText(this, "Debe describir el problema", Toast.LENGTH_SHORT).show()
            return
        }
        
        val moldeSeleccionado = moldes[spMolde.selectedItemPosition]
        val subtipo = findViewById<Spinner>(R.id.sp_subtipo).selectedItem.toString()
        
        val solicitud = SolicitudMantenimiento(
            moldeId = moldeSeleccionado.id,
            departamentoOrigen = findViewById<Spinner>(R.id.sp_departamento).selectedItem.toString(),
            tipo = findViewById<Spinner>(R.id.sp_tipo).selectedItem.toString(),
            subtipo = subtipo,
            prioridad = findViewById<Spinner>(R.id.sp_prioridad).selectedItem.toString(),
            problemaReportado = problema,
            afectaProduccion = findViewById<CheckBox>(R.id.cb_afecta_produccion).isChecked,
            comentariosSolicitante = findViewById<EditText>(R.id.et_comentarios).text.toString(),
            
            detalleConexion = if (subtipo.contains("Conexión") || subtipo.contains("Manguera")) {
                findViewById<Spinner>(R.id.sp_detalle_conexion).selectedItem.toString()
            } else "",
            
            tipoConexionY = if (subtipo == "Cambio de Conexión Rápida") {
                findViewById<CheckBox>(R.id.cb_conexion_y).isChecked
            } else false,
            
            calibreManguera = if (subtipo == "Cambio de Manguera") {
                findViewById<Spinner>(R.id.sp_calibre).selectedItem.toString()
            } else "",
            
            cantidadMangueras = if (subtipo == "Cambio de Manguera") {
                findViewById<EditText>(R.id.et_cantidad_mangueras).text.toString().toIntOrNull() ?: 0
            } else 0
        )
        
        lifecycleScope.launch {
            try {
                database.solicitudMantenimientoDao().insert(solicitud)
                Toast.makeText(this@SolicitudMantenimientoFormActivity, 
                    "Solicitud enviada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@SolicitudMantenimientoFormActivity, 
                    "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
