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
import com.example.moldescmms.data.entities.Producto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProductosListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val productos = mutableListOf<Producto>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_productos)
        val btnAdd = findViewById<Button>(R.id.btn_add_producto)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadProductos()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, ProductoFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val producto = productos[position]
            val intent = Intent(this, ProductoFormActivity::class.java)
            intent.putExtra("PRODUCTO_ID", producto.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadProductos()
    }
    
    private fun loadProductos() {
        lifecycleScope.launch {
            val list = database.productoDao().getAllActivos().first()
            productos.clear()
            productos.addAll(list)
            
            val items = productos.map {
                "${it.codigo} - ${it.nombre}\nStock: ${it.stockActual} | ${it.tipo}"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}
