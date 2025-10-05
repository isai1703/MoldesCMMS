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
import com.example.moldescmms.data.entities.Mantenimiento
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MantenimientosListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val mantenimientos = mutableListOf<Mantenimiento>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimientos_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_mantenimientos)
        val btnAdd = findViewById<Button>(R.id.btn_add_mantenimiento)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadMantenimientos()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, MantenimientoFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val mantenimiento = mantenimientos[position]
            val intent = Intent(this, MantenimientoFormActivity::class.java)
            intent.putExtra("MANTENIMIENTO_ID", mantenimiento.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadMantenimientos()
    }
    
    private fun loadMantenimientos() {
        lifecycleScope.launch {
            val allMant = database.mantenimientoDao().getAllList()
            mantenimientos.clear()
            mantenimientos.addAll(allMant)
            
            val items = mantenimientos.map {
                val fecha = dateFormat.format(Date(it.fechaProgramada))
                "${it.tipo} - ${it.estado}\n$fecha"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}

// Extensión para obtener lista
suspend fun com.example.moldescmms.data.daos.MantenimientoDao.getAllList(): List<Mantenimiento> {
    var result = listOf<Mantenimiento>()
    getAll().collect { result = it }
    return result
}
