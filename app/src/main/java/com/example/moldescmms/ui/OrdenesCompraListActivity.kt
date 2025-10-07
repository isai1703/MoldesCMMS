package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.OrdenCompra
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OrdenesCompraListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val ordenes = mutableListOf<OrdenCompra>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordenes_compra_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_ordenes)
        val btnAdd = findViewById<Button>(R.id.btn_add_orden)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadOrdenes()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, OrdenCompraFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val orden = ordenes[position]
            val intent = Intent(this, OrdenCompraFormActivity::class.java)
            intent.putExtra("ORDEN_ID", orden.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadOrdenes()
    }
    
    private fun loadOrdenes() {
        lifecycleScope.launch {
            val list = database.ordenCompraDao().getAll().first()
            ordenes.clear()
            ordenes.addAll(list)
            
            val items = ordenes.map {
                val fecha = dateFormat.format(Date(it.fechaOrden))
                "${it.numeroOrden} - ${it.proveedor}\n$fecha | ${it.estado} | $${it.total}"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}
