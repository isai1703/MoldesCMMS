package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.OrdenCompra
import kotlinx.coroutines.launch

class OrdenCompraFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_compra_form)
        
        database = AppDatabase.getDatabase(this)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nueva Orden de Compra"
        
        findViewById<Button>(R.id.btn_save_orden)?.setOnClickListener {
            saveOrden()
        }
        
        findViewById<Button>(R.id.btn_cancel_orden)?.setOnClickListener {
            finish()
        }
    }
    
    private fun saveOrden() {
        val numeroOrden = findViewById<EditText>(R.id.et_numero_orden)?.text.toString()
        val proveedor = findViewById<EditText>(R.id.et_proveedor_orden)?.text.toString()
        
        if (numeroOrden.isEmpty() || proveedor.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val orden = OrdenCompra(
            numeroOrden = numeroOrden,
            departamentoSolicitante = "Producción",
            proveedor = proveedor
        )
        
        lifecycleScope.launch {
            try {
                database.ordenCompraDao().insert(orden)
                Toast.makeText(this@OrdenCompraFormActivity, "Guardado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@OrdenCompraFormActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
