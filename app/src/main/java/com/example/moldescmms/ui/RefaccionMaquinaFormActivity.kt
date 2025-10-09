package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.RefaccionMaquina
import kotlinx.coroutines.launch

class RefaccionMaquinaFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var refaccionId: Long = 0
    private var isEditMode = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refaccion_maquina_form)
        
        database = AppDatabase.getDatabase(this)
        
        refaccionId = intent.getLongExtra("REFACCION_ID", 0)
        isEditMode = refaccionId > 0
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isEditMode) "Editar Refacción" else "Nueva Refacción"
        
        setupSpinners()
        
        if (isEditMode) {
            loadRefaccion()
        }
        
        findViewById<Button>(R.id.btn_save_refaccion_maquina).setOnClickListener {
            saveRefaccion()
        }
        
        findViewById<Button>(R.id.btn_cancel_refaccion_maquina).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val categorias = arrayOf(
            "Componentes Eléctricos",
            "Componentes Mecánicos",
            "Componentes Hidráulicos",
            "Componentes Neumáticos",
            "Sensores y Actuadores",
            "Filtros y Sellos",
            "Rodamientos y Cojinetes",
            "Bandas y Correas",
            "Otros"
        )
        val categoriaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_categoria_refaccion).adapter = categoriaAdapter
        
        val unidades = arrayOf("Pieza", "Juego", "Metro", "Litro", "Kilogramo", "Caja")
        val unidadAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unidades)
        unidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_unidad_medida_refaccion).adapter = unidadAdapter
    }
    
    private fun loadRefaccion() {
        lifecycleScope.launch {
            val refaccion = database.refaccionMaquinaDao().getById(refaccionId)
            refaccion?.let {
                findViewById<EditText>(R.id.et_nombre_refaccion).setText(it.nombre)
                findViewById<EditText>(R.id.et_codigo_refaccion).setText(it.codigoRefaccion)
                findViewById<EditText>(R.id.et_numero_parte).setText(it.numeroParte)
                findViewById<EditText>(R.id.et_modelo_maquina).setText(it.modeloMaquina)
                findViewById<EditText>(R.id.et_modelos_compatibles).setText(it.modelosCompatibles)
                findViewById<EditText>(R.id.et_descripcion_refaccion).setText(it.descripcion)
                
                val catPos = (findViewById<Spinner>(R.id.sp_categoria_refaccion).adapter as ArrayAdapter<String>)
                    .getPosition(it.categoria)
                if (catPos >= 0) findViewById<Spinner>(R.id.sp_categoria_refaccion).setSelection(catPos)
                
                findViewById<EditText>(R.id.et_stock_actual).setText(it.stockActual.toString())
                findViewById<EditText>(R.id.et_stock_minimo).setText(it.stockMinimo.toString())
                findViewById<EditText>(R.id.et_stock_maximo).setText(it.stockMaximo.toString())
                
                val unidadPos = (findViewById<Spinner>(R.id.sp_unidad_medida_refaccion).adapter as ArrayAdapter<String>)
                    .getPosition(it.unidadMedida)
                if (unidadPos >= 0) findViewById<Spinner>(R.id.sp_unidad_medida_refaccion).setSelection(unidadPos)
                
                findViewById<EditText>(R.id.et_ubicacion_almacen).setText(it.ubicacionAlmacen)
                findViewById<EditText>(R.id.et_precio_unitario).setText(it.precioUnitario.toString())
                findViewById<EditText>(R.id.et_proveedor_refaccion).setText(it.proveedor)
                findViewById<EditText>(R.id.et_tiempo_entrega).setText(it.tiempoEntregaDias.toString())
                findViewById<EditText>(R.id.et_especificaciones_refaccion).setText(it.especificaciones)
            }
        }
    }
    
    private fun saveRefaccion() {
        val nombre = findViewById<EditText>(R.id.et_nombre_refaccion).text.toString()
        val codigo = findViewById<EditText>(R.id.et_codigo_refaccion).text.toString()
        val numeroParte = findViewById<EditText>(R.id.et_numero_parte).text.toString()
        val modeloMaquina = findViewById<EditText>(R.id.et_modelo_maquina).text.toString()
        
        if (nombre.isEmpty() || codigo.isEmpty() || numeroParte.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val stockActual = findViewById<EditText>(R.id.et_stock_actual).text.toString().toIntOrNull() ?: 0
        val stockMinimo = findViewById<EditText>(R.id.et_stock_minimo).text.toString().toIntOrNull() ?: 0
        val stockMaximo = findViewById<EditText>(R.id.et_stock_maximo).text.toString().toIntOrNull() ?: 0
        
        val refaccion = RefaccionMaquina(
            codigo = codigoRefaccion,
            id = if (isEditMode) refaccionId else 0,
            codigoRefaccion = codigo,
            nombre = nombre,
            descripcion = findViewById<EditText>(R.id.et_descripcion_refaccion).text.toString(),
            numeroParte = numeroParte,
            categoria = findViewById<Spinner>(R.id.sp_categoria_refaccion).selectedItem.toString(),
            modeloMaquina = modeloMaquina,
            modelosCompatibles = findViewById<EditText>(R.id.et_modelos_compatibles).text.toString(),
            stockActual = stockActual,
            stockMinimo = stockMinimo,
            stockMaximo = stockMaximo,
            unidadMedida = findViewById<Spinner>(R.id.sp_unidad_medida_refaccion).selectedItem.toString(),
            ubicacionAlmacen = findViewById<EditText>(R.id.et_ubicacion_almacen).text.toString(),
            precioUnitario = findViewById<EditText>(R.id.et_precio_unitario).text.toString().toDoubleOrNull() ?: 0.0,
            proveedor = findViewById<EditText>(R.id.et_proveedor_refaccion).text.toString(),
            tiempoEntregaDias = findViewById<EditText>(R.id.et_tiempo_entrega).text.toString().toIntOrNull() ?: 0,
            especificaciones = findViewById<EditText>(R.id.et_especificaciones_refaccion).text.toString(),
            fechaActualizacion = System.currentTimeMillis()
        )
        
        lifecycleScope.launch {
            try {
                database.refaccionMaquinaDao().insert(refaccion)
                Toast.makeText(this@RefaccionMaquinaFormActivity,
                    "Refacción guardada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@RefaccionMaquinaFormActivity,
                    "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
