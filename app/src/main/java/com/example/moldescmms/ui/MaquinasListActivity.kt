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
import com.example.moldescmms.data.entities.Maquina
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MaquinasListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val maquinas = mutableListOf<Maquina>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maquinas_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_maquinas)
        val btnAdd = findViewById<Button>(R.id.btn_add_maquina)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadMaquinas()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, MaquinaFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val maquina = maquinas[position]
            val intent = Intent(this, MaquinaFormActivity::class.java)
            intent.putExtra("MAQUINA_ID", maquina.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadMaquinas()
    }
    
    private fun loadMaquinas() {
        lifecycleScope.launch {
            val list = database.maquinaDao().getAll().first()
            maquinas.clear()
            maquinas.addAll(list)
            
            val items = maquinas.map {
                "${it.codigo} - ${it.nombre}\n${it.tipo} | ${it.estado}"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}
