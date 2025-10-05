package com.example.moldescmms.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Mantenimiento
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MantenimientoFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var mantenimientoId: Long = -1
    private var fechaSeleccionada: Long = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val moldes = mutableListOf<Molde>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_form)
        
        database = AppDatabase.getDatabase(this)
        mantenimientoId = intent.getLongExtra("MANTENIMIENTO_ID", -1)
        
        setupSpinners()
        setupDatePicker()
        
        if (mantenimientoId != -1L) {
            loadMantenimiento()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveMantenimiento()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        lifecycleScope.launch {
            moldes.clear()
            moldes.addAll(database.moldeDao().getAllList())
            
            val moldesNames = moldes.map { "${it.codigo} - ${it.nombre}" }
            val moldeAdapter = ArrayAdapter(this@MantenimientoFormActivity, 
                android.R.layout.simple_spinner_item, moldesNames)
            moldeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.sp_molde).adapter = moldeAdapter
        }
        
        val tipos = arrayOf("Preventivo", "Correctivo")
        val tipoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_tipo).adapter = tipoAdapter
        
        val estados = arrayOf("Pendiente", "En Proceso", "Completado", "Cancelado")
        val estadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_estado).adapter = estadoAdapter
        
        val prioridades = arrayOf("Alta", "Media", "Baja")
        val prioridadAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        prioridadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_prioridad).adapter = prioridadAdapter
    }
    
    private fun setupDatePicker() {
        val btnFecha = findViewById<Button>(R.id.btn_fecha)
        btnFecha.text = dateFormat.format(Date(fechaSeleccionada))
        
        btnFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = fechaSeleccionada
            
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    fechaSeleccionada = calendar.timeInMillis
                    btnFecha.text = dateFormat.format(Date(fechaSeleccionada))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    
    private fun loadMantenimiento() {
        lifecycleScope.launch {
            val mant = database.mantenimientoDao().getById(mantenimientoId)
            mant?.let {
                fechaSeleccionada = it.fechaProgramada
                findViewById<Button>(R.id.btn_fecha).text = dateFormat.format(Date(fechaSeleccionada))
                findViewById<EditText>(R.id.et_subtipo).setText(it.subtipo)
                findViewById<EditText>(R.id.et_descripcion).setText(it.descripcion)
                findViewById<EditText>(R.id.et_trabajos).setText(it.trabajosRealizados)
                findViewById<EditText>(R.id.et_realizado_por).setText(it.realizadoPor)
                findViewById<EditText>(R.id.et_supervisado_por).setText(it.supervisadoPor)
                findViewById<EditText>(R.id.et_observaciones).setText(it.observaciones)
                
                val moldeIndex = moldes.indexOfFirst { m -> m.id == it.moldeId }
                if (moldeIndex >= 0) {
                    findViewById<Spinner>(R.id.sp_molde).setSelection(moldeIndex)
                }
                
                val tipoIndex = when(it.tipo) {
                    "Preventivo" -> 0
                    "Correctivo" -> 1
                    else -> 0
                }
                findViewById<Spinner>(R.id.sp_tipo).setSelection(tipoIndex)
                
                val estadoIndex = when(it.estado) {
                    "Pendiente" -> 0
                    "En Proceso" -> 1
                    "Completado" -> 2
                    "Cancelado" -> 3
                    else -> 0
                }
                findViewById<Spinner>(R.id.sp_estado).setSelection(estadoIndex)
                
                val prioridadIndex = when(it.prioridad) {
                    "Alta" -> 0
                    "Media" -> 1
                    "Baja" -> 2
                    else -> 1
                }
                findViewById<Spinner>(R.id.sp_prioridad).setSelection(prioridadIndex)
            }
        }
    }
    
    private fun saveMantenimiento() {
        val spMolde = findViewById<Spinner>(R.id.sp_molde)
        if (moldes.isEmpty() || spMolde.selectedItemPosition < 0) {
            Toast.makeText(this, "Debe seleccionar un molde", Toast.LENGTH_SHORT).show()
            return
        }
        
        val moldeSeleccionado = moldes[spMolde.selectedItemPosition]
        
        val mantenimiento = Mantenimiento(
            id = if (mantenimientoId == -1L) 0 else mantenimientoId,
            moldeId = moldeSeleccionado.id,
            tipo = findViewById<Spinner>(R.id.sp_tipo).selectedItem.toString(),
            subtipo = findViewById<EditText>(R.id.et_subtipo).text.toString(),
            estado = findViewById<Spinner>(R.id.sp_estado).selectedItem.toString(),
            prioridad = findViewById<Spinner>(R.id.sp_prioridad).selectedItem.toString(),
            fechaProgramada = fechaSeleccionada,
            descripcion = findViewById<EditText>(R.id.et_descripcion).text.toString(),
            trabajosRealizados = findViewById<EditText>(R.id.et_trabajos).text.toString(),
            realizadoPor = findViewById<EditText>(R.id.et_realizado_por).text.toString(),
            supervisadoPor = findViewById<EditText>(R.id.et_supervisado_por).text.toString(),
            observaciones = findViewById<EditText>(R.id.et_observaciones).text.toString()
        )
        
        lifecycleScope.launch {
            database.mantenimientoDao().insert(mantenimiento)
            Toast.makeText(this@MantenimientoFormActivity, "Mantenimiento guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
