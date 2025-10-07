package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Producto
import kotlinx.coroutines.launch

class ProductoFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var productoId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_form)
        
        database = AppDatabase.getDatabase(this)
        productoId = intent.getLongExtra("PRODUCTO_ID", -1)
        
        setupSpinners()
        
        if (productoId != -1L) {
            loadProducto()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveProducto()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val tipos = arrayOf("Terminado", "Semi-terminado", "Materia Prima")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_tipo).adapter = adapter
    }
    
    private fun loadProducto() {
        lifecycleScope.launch {
            val prod = database.productoDao().getById(productoId)
            prod?.let {
                findViewById<EditText>(R.id.et_codigo).setText(it.codigo)
                findViewById<EditText>(R.id.et_nombre).setText(it.nombre)
                findViewById<EditText>(R.id.et_stock_actual).setText(it.stockActual.toString())
                findViewById<EditText>(R.id.et_stock_minimo).setText(it.stockMinimo.toString())
                findViewById<EditText>(R.id.et_ubicacion).setText(it.ubicacionAlmacen)
                findViewById<EditText>(R.id.et_descripcion).setText(it.descripcion)
            }
        }
    }
    
    private fun saveProducto() {
        val codigo = findViewById<EditText>(R.id.et_codigo).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Código y nombre son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val producto = Producto(
            id = if (productoId == -1L) 0 else productoId,
            codigo = codigo,
            nombre = nombre,
            descripcion = findViewById<EditText>(R.id.et_descripcion).text.toString(),
            tipo = findViewById<Spinner>(R.id.sp_tipo).selectedItem.toString(),
            stockActual = findViewById<EditText>(R.id.et_stock_actual).text.toString().toIntOrNull() ?: 0,
            stockMinimo = findViewById<EditText>(R.id.et_stock_minimo).text.toString().toIntOrNull() ?: 0,
            ubicacionAlmacen = findViewById<EditText>(R.id.et_ubicacion).text.toString()
        )
        
        lifecycleScope.launch {
            database.productoDao().insert(producto)
            Toast.makeText(this@ProductoFormActivity, "Producto guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
