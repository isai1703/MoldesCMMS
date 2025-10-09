package com.example.moldescmms.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.RequerimientoInsumo
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RequerimientoInsumoFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var fechaRequerida: Long = System.currentTimeMillis()
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requerimiento_insumo_form)
        
        database = AppDatabase.getDatabase(this)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nuevo Requerimiento"
        
        setupSpinners()
        setupDatePicker()
        
        findViewById<Button>(R.id.btn_save_requerimiento).setOnClickListener {
            saveRequerimiento()
        }
        
        findViewById<Button>(R.id.btn_cancel_requerimiento).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val tipos = arrayOf(
            "Materia Prima",
            "Material de Empaque",
            "Insumos de Producción",
            "Herramientas",
            "Refacciones",
            "Equipo de Seguridad",
            "Limpieza y Mantenimiento"
        )
        val tipoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_tipo_insumo).adapter = tipoAdapter
        
        val prioridades = arrayOf("Urgente", "Alta", "Media", "Baja")
        val prioridadAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        prioridadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_prioridad_insumo).adapter = prioridadAdapter
        findViewById<Spinner>(R.id.sp_prioridad_insumo).setSelection(2)
        
        val areas = arrayOf("Producción", "Calidad", "Mantenimiento", "Almacén")
        val areaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areas)
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_area_solicitante).adapter = areaAdapter
    }
    
    private fun setupDatePicker() {
        findViewById<TextView>(R.id.tv_fecha_requerida).text = dateFormat.format(Date(fechaRequerida))
        
        findViewById<Button>(R.id.btn_fecha_requerida).setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = fechaRequerida
            
            DatePickerDialog(this, { _, year, month, day ->
                calendar.set(year, month, day)
                fechaRequerida = calendar.timeInMillis
                findViewById<TextView>(R.id.tv_fecha_requerida).text = dateFormat.format(Date(fechaRequerida))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    
    private fun saveRequerimiento() {
        val articulo = findViewById<EditText>(R.id.et_articulo).text.toString()
        val cantidad = findViewById<EditText>(R.id.et_cantidad_insumo).text.toString().toIntOrNull()
        val supervisor = findViewById<EditText>(R.id.et_supervisor).text.toString()
        
        if (articulo.isEmpty() || cantidad == null || cantidad <= 0) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (supervisor.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del supervisor", Toast.LENGTH_SHORT).show()
            return
        }
        
        val requerimiento = RequerimientoInsumo(
            tipoInsumo = findViewById<Spinner>(R.id.sp_tipo_insumo).selectedItem.toString(),
            articulo = articulo,
            descripcion = findViewById<EditText>(R.id.et_descripcion_insumo).text.toString(),
            cantidad = cantidad,
            unidadMedida = findViewById<EditText>(R.id.et_unidad_medida).text.toString(),
            areaSolicitante = findViewById<Spinner>(R.id.sp_area_solicitante).selectedItem.toString(),
            solicitadoPor = supervisor,
            fechaRequerida = fechaRequerida,
            prioridad = findViewById<Spinner>(R.id.sp_prioridad_insumo).selectedItem.toString(),
            justificacion = findViewById<EditText>(R.id.et_justificacion).text.toString(),
            costoEstimado = findViewById<EditText>(R.id.et_costo_estimado).text.toString().toDoubleOrNull() ?: 0.0,
            proveedorSugerido = findViewById<EditText>(R.id.et_proveedor_sugerido).text.toString(),
            especificacionesTecnicas = findViewById<EditText>(R.id.et_especificaciones).text.toString(),
            observaciones = findViewById<EditText>(R.id.et_observaciones_insumo).text.toString()
        )
        
        lifecycleScope.launch {
            try {
                database.requerimientoInsumoDao().insert(requerimiento)
                Toast.makeText(this@RequerimientoInsumoFormActivity,
                    "Requerimiento creado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@RequerimientoInsumoFormActivity,
                    "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
