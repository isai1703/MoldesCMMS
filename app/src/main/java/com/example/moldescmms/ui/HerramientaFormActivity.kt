package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Herramienta
import kotlinx.coroutines.launch

class HerramientaFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var herramientaId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_herramienta_form)
        
        database = AppDatabase.getDatabase(this)
        herramientaId = intent.getLongExtra("HERRAMIENTA_ID", -1)
        
        setupSpinners()
        
        if (herramientaId != -1L) {
            loadHerramienta()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveHerramienta()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val categorias = arrayOf("Manual", "Eléctrica", "Medición", "Corte", "Pulido", "Neumática", "Otra")
        val catAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_categoria).adapter = catAdapter
        
        val estados = arrayOf("Nuevo", "Bueno", "Regular", "Malo", "Fuera de Servicio")
        val estadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_estado).adapter = estadoAdapter
        findViewById<Spinner>(R.id.sp_estado).setSelection(1)
    }
    
    private fun loadHerramienta() {
        lifecycleScope.launch {
            val herr = database.herramientaDao().getById(herramientaId)
            herr?.let {
                findViewById<EditText>(R.id.et_codigo).setText(it.codigo)
                findViewById<EditText>(R.id.et_nombre).setText(it.nombre)
                findViewById<EditText>(R.id.et_marca).setText(it.marca)
                findViewById<EditText>(R.id.et_ubicacion).setText(it.ubicacion)
                findViewById<EditText>(R.id.et_cantidad).setText(it.cantidad.toString())
                findViewById<EditText>(R.id.et_descripcion).setText(it.descripcion)
            }
        }
    }
    
    private fun saveHerramienta() {
        val codigo = findViewById<EditText>(R.id.et_codigo).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Código y nombre son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val herramienta = Herramienta(
            id = if (herramientaId == -1L) 0 else herramientaId,
            codigo = codigo,
            nombre = nombre,
            marca = findViewById<EditText>(R.id.et_marca).text.toString(),
            categoria = findViewById<Spinner>(R.id.sp_categoria).selectedItem.toString(),
            ubicacion = findViewById<EditText>(R.id.et_ubicacion).text.toString(),
            cantidad = findViewById<EditText>(R.id.et_cantidad).text.toString().toIntOrNull() ?: 1,
            estado = findViewById<Spinner>(R.id.sp_estado).selectedItem.toString(),
            descripcion = findViewById<EditText>(R.id.et_descripcion).text.toString()
        )
        
        lifecycleScope.launch {
            database.herramientaDao().insert(herramienta)
            Toast.makeText(this@HerramientaFormActivity, "Herramienta guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
