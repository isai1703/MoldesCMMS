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
    private var ordenId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden_compra_form)
        
        database = AppDatabase.getDatabase(this)
        ordenId = intent.getLongExtra("ORDEN_ID", -1)
        
        setupSpinners()
        
        if (ordenId != -1L) {
            loadOrden()
        }
        
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveOrden()
        }
        
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
    }
    
    private fun setupSpinners() {
        val estados = arrayOf("Pendiente", "Aprobada", "En Tránsito", "Recibida", "Cancelada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.sp_estado).adapter = adapter
    }
    
    private fun loadOrden() {
        lifecycleScope.launch {
            val orden = database.ordenCompraDao().getById(ordenId)
            orden?.let {
                findViewById<EditText>(R.id.et_numero_orden).setText(it.numeroOrden)
                findViewById<EditText>(R.id.et_proveedor).setText(it.proveedor)
                findViewById<EditText>(R.id.et_items).setText(it.items)
                findViewById<EditText>(R.id.et_total).setText(it.total.toString())
                findViewById<EditText>(R.id.et_solicitado_por).setText(it.solicitadoPor)
                findViewById<EditText>(R.id.et_observaciones).setText(it.observaciones)
            }
        }
    }
    
    private fun saveOrden() {
        val numeroOrden = findViewById<EditText>(R.id.et_numero_orden).text.toString()
        val proveedor = findViewById<EditText>(R.id.et_proveedor).text.toString()
        
        if (numeroOrden.isEmpty() || proveedor.isEmpty()) {
            Toast.makeText(this, "Número de orden y proveedor son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        
        val orden = OrdenCompra(
            id = if (ordenId == -1L) 0 else ordenId,
            numeroOrden = numeroOrden,
            proveedor = proveedor,
            estado = findViewById<Spinner>(R.id.sp_estado).selectedItem.toString(),
            items = findViewById<EditText>(R.id.et_items).text.toString(),
            total = findViewById<EditText>(R.id.et_total).text.toString().toDoubleOrNull() ?: 0.0,
            solicitadoPor = findViewById<EditText>(R.id.et_solicitado_por).text.toString(),
            observaciones = findViewById<EditText>(R.id.et_observaciones).text.toString()
        )
        
        lifecycleScope.launch {
            database.ordenCompraDao().insert(orden)
            Toast.makeText(this@OrdenCompraFormActivity, "Orden guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
