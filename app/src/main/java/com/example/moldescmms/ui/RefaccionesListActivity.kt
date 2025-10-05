package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Refaccion
import kotlinx.coroutines.launch

class RefaccionesListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val refacciones = mutableListOf<Refaccion>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refacciones_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_refacciones)
        val btnAdd = findViewById<Button>(R.id.btn_add_refaccion)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadRefacciones()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, RefaccionFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val refaccion = refacciones[position]
            val intent = Intent(this, RefaccionFormActivity::class.java)
            intent.putExtra("REFACCION_ID", refaccion.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadRefacciones()
    }
    
    private fun loadRefacciones() {
        lifecycleScope.launch {
            database.refaccionDao().getAll().collect { list ->
                refacciones.clear()
                refacciones.addAll(list)
                
                val bajoStock = refacciones.count { it.stockActual <= it.stockMinimo }
                if (bajoStock > 0) {
                    findViewById<TextView>(R.id.tv_alertas).apply {
                        text = "⚠️ $bajoStock refacciones con stock bajo"
                        visibility = android.view.View.VISIBLE
                    }
                }
                
                val items = refacciones.map {
                    val estado = if (it.stockActual <= it.stockMinimo) "⚠️" else ""
                    "${it.codigo} - ${it.nombre} $estado\nStock: ${it.stockActual}/${it.stockMinimo}"
                }
                
                adapter.clear()
                adapter.addAll(items)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
