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
import com.example.moldescmms.data.entities.Herramienta
import kotlinx.coroutines.launch

class HerramientasListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val herramientas = mutableListOf<Herramienta>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_herramientas_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_herramientas)
        val btnAdd = findViewById<Button>(R.id.btn_add_herramienta)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadHerramientas()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, HerramientaFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val herramienta = herramientas[position]
            val intent = Intent(this, HerramientaFormActivity::class.java)
            intent.putExtra("HERRAMIENTA_ID", herramienta.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadHerramientas()
    }
    
    private fun loadHerramientas() {
        lifecycleScope.launch {
            herramientas.clear()
            getAll().collect { list ->
                herramientas.addAll(list)
                
                val items = herramientas.map {
                    "${it.codigo} - ${it.nombre}\nCantidad: ${it.cantidad} | ${it.estado}"
                }
                
                adapter.clear()
                adapter.addAll(items)
                adapter.notifyDataSetChanged()
            }
        }
    }
    
    private fun getAll() = database.herramientaDao().getAll()
}
