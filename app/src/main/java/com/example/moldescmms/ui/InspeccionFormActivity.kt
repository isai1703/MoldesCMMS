package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.InspeccionCalidad
import com.example.moldescmms.data.entities.Producto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InspeccionFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var inspeccionId: Long = -1
    private val productos = mutableListOf<Producto>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspeccion_form)
        
        database = AppDatabase.getDatabase(this)
        inspeccionId = intent.getLongExtra("INSPECCION_ID", -1)
        
        setupSpinners()
        
        if (inspeccionId != -1L) {
            loadInspeccion()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveInspeccion()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        lifecycleScope.launch {
            productos.clear()
            productos.addAll(database.productoDao().getAllActivos().first())
            
            val productosNames = productos.map { "${it.codigo} - ${it.nombre}" }
            val productoAdapter = ArrayAdapter(this@InspeccionFormActivity, 
                android.R.layout.simple_spinner_item, productosNames)
            productoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.sp_producto).adapter = productoAdapter
        }
        
        val resultados = arrayOf("Aprobado", "Rechazado", "Condicional")
        val resultadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resultados)
        resultadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_resultado).adapter = resultadoAdapter
    }
    
    private fun loadInspeccion() {
        lifecycleScope.launch {
            val insp = database.inspeccionCalidadDao().getById(inspeccionId)
            insp?.let {
                findViewById<EditText>(R.id.et_lote).setText(it.loteNumero)
                findViewById<EditText>(R.id.et_cantidad_inspeccionada).setText(it.cantidadInspeccionada.toString())
                findViewById<EditText>(R.id.et_cantidad_aprobada).setText(it.cantidadAprobada.toString())
                findViewById<EditText>(R.id.et_cantidad_rechazada).setText(it.cantidadRechazada.toString())
                findViewById<EditText>(R.id.et_inspector).setText(it.inspector)
                findViewById<EditText>(R.id.et_defectos).setText(it.defectosEncontrados)
                findViewById<EditText>(R.id.et_observaciones).setText(it.observaciones)
            }
        }
    }
    
    private fun saveInspeccion() {
        val spProducto = findViewById<Spinner>(R.id.sp_producto)
        if (productos.isEmpty() || spProducto.selectedItemPosition < 0) {
            Toast.makeText(this, "Debe seleccionar un producto", Toast.LENGTH_SHORT).show()
            return
        }
        
        val productoSeleccionado = productos[spProducto.selectedItemPosition]
        
        val inspeccion = InspeccionCalidad(
            id = if (inspeccionId == -1L) 0 else inspeccionId,
            productoId = productoSeleccionado.id,
            loteNumero = findViewById<EditText>(R.id.et_lote).text.toString(),
            cantidadInspeccionada = findViewById<EditText>(R.id.et_cantidad_inspeccionada).text.toString().toIntOrNull() ?: 0,
            cantidadAprobada = findViewById<EditText>(R.id.et_cantidad_aprobada).text.toString().toIntOrNull() ?: 0,
            cantidadRechazada = findViewById<EditText>(R.id.et_cantidad_rechazada).text.toString().toIntOrNull() ?: 0,
            resultado = findViewById<Spinner>(R.id.sp_resultado).selectedItem.toString(),
            inspector = findViewById<EditText>(R.id.et_inspector).text.toString(),
            defectosEncontrados = findViewById<EditText>(R.id.et_defectos).text.toString(),
            observaciones = findViewById<EditText>(R.id.et_observaciones).text.toString()
        )
        
        lifecycleScope.launch {
            database.inspeccionCalidadDao().insert(inspeccion)
            Toast.makeText(this@InspeccionFormActivity, "Inspección guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

suspend fun com.example.moldescmms.data.daos.InspeccionCalidadDao.getById(id: Long): InspeccionCalidad? {
    return getAll().first().find { it.id == id }
}
