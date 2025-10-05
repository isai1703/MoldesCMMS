package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Refaccion
import kotlinx.coroutines.launch

class RefaccionFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private var refaccionId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refaccion_form)
        
        database = AppDatabase.getDatabase(this)
        refaccionId = intent.getLongExtra("REFACCION_ID", -1)
        
        setupSpinners()
        
        if (refaccionId != -1L) {
            loadRefaccion()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveRefaccion()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val categorias = arrayOf(
            "Expulsores",
            "Resortes",
            "Pasadores",
            "Conexiones Rápidas",
            "Sellos",
            "O-Rings",
            "Bujes",
            "Pernos",
            "Otra"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_categoria).adapter = adapter
    }
    
    private fun loadRefaccion() {
        lifecycleScope.launch {
            val ref = database.refaccionDao().getById(refaccionId)
            ref?.let {
                findViewById<EditText>(R.id.et_codigo).setText(it.codigo)
                findViewById<EditText>(R.id.et_nombre).setText(it.nombre)
                findViewById<EditText>(R.id.et_stock_actual).setText(it.stockActual.toString())
                findViewById<EditText>(R.id.et_stock_minimo).setText(it.stockMinimo.toString())
                findViewById<EditText>(R.id.et_ubicacion).setText(it.ubicacionAlmacen)
                findViewById<EditText>(R.id.et_proveedor).setText(it.proveedorPrincipal)
                findViewById<EditText>(R.id.et_costo).setText(it.costoUnitario.toString())
                findViewById<EditText>(R.id.et_descripcion).setText(it.descripcion)
            }
        }
    }
    
    private fun saveRefaccion() {
        val codigo = findViewById<EditText>(R.id.et_codigo).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Código y nombre son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val stockActual = findViewById<EditText>(R.id.et_stock_actual).text.toString().toIntOrNull() ?: 0
        val stockMinimo = findViewById<EditText>(R.id.et_stock_minimo).text.toString().toIntOrNull() ?: 5
        
        val refaccion = Refaccion(
            id = if (refaccionId == -1L) 0 else refaccionId,
            codigo = codigo,
            nombre = nombre,
            categoria = findViewById<Spinner>(R.id.sp_categoria).selectedItem.toString(),
            stockActual = stockActual,
            stockMinimo = stockMinimo,
            ubicacionAlmacen = findViewById<EditText>(R.id.et_ubicacion).text.toString(),
            proveedorPrincipal = findViewById<EditText>(R.id.et_proveedor).text.toString(),
            costoUnitario = findViewById<EditText>(R.id.et_costo).text.toString().toDoubleOrNull() ?: 0.0,
            descripcion = findViewById<EditText>(R.id.et_descripcion).text.toString(),
            requiereReorden = stockActual <= stockMinimo,
            estadoInventario = when {
                stockActual == 0 -> "Agotado"
                stockActual <= stockMinimo -> "Crítico"
                stockActual <= stockMinimo * 2 -> "Bajo"
                else -> "Normal"
            }
        )
        
        lifecycleScope.launch {
            database.refaccionDao().insert(refaccion)
            Toast.makeText(this@RefaccionFormActivity, "Refacción guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
