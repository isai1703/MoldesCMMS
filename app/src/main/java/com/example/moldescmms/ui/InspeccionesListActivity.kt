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
import com.example.moldescmms.data.entities.InspeccionCalidad
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InspeccionesListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val inspecciones = mutableListOf<InspeccionCalidad>()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspecciones_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_inspecciones)
        val btnAdd = findViewById<Button>(R.id.btn_add_inspeccion)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadInspecciones()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, InspeccionFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val inspeccion = inspecciones[position]
            val intent = Intent(this, InspeccionFormActivity::class.java)
            intent.putExtra("INSPECCION_ID", inspeccion.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadInspecciones()
    }
    
    private fun loadInspecciones() {
        lifecycleScope.launch {
            val list = database.inspeccionCalidadDao().getAll().first()
            inspecciones.clear()
            inspecciones.addAll(list)
            
            val items = inspecciones.map {
                val fecha = dateFormat.format(Date(it.fechaInspeccion))
                "Lote: ${it.loteNumero} - ${it.resultado}\n$fecha"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}
