package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.launch

class MoldesListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private val moldes = mutableListOf<Molde>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moldes_list)
        
        database = AppDatabase.getDatabase(this)
        
        val listView = findViewById<ListView>(R.id.lv_moldes)
        val btnAdd = findViewById<Button>(R.id.btn_add_molde)
        
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        
        loadMoldes()
        
        btnAdd.setOnClickListener {
            startActivity(Intent(this, MoldeFormActivity::class.java))
        }
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val molde = moldes[position]
            val intent = Intent(this, MoldeFormActivity::class.java)
            intent.putExtra("MOLDE_ID", molde.id)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadMoldes()
    }
    
    private fun loadMoldes() {
        lifecycleScope.launch {
            moldes.clear()
            moldes.addAll(database.moldeDao().getAllList())
            
            val items = moldes.map { 
                "${it.codigo} - ${it.nombre}\n${it.estado}"
            }
            
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }
}
