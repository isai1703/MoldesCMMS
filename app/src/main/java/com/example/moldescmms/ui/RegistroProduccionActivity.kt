package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.ui.adapters.RegistroProduccionAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class RegistroProduccionActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private lateinit var adapter: RegistroProduccionAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_produccion)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registros de Producción"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_registros_produccion)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = RegistroProduccionAdapter { registro ->
            val intent = Intent(this, RegistroProduccionFormActivity::class.java)
            intent.putExtra("REGISTRO_ID", registro.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        
        findViewById<FloatingActionButton>(R.id.fab_add_registro)?.setOnClickListener {
            startActivity(Intent(this, RegistroProduccionFormActivity::class.java))
        }
        
        loadRegistros()
    }
    
    override fun onResume() {
        super.onResume()
        loadRegistros()
    }
    
    private fun loadRegistros() {
        lifecycleScope.launch {
            try {
                database.registroProduccionDao().getAll().collect { registros ->
                    adapter.submitList(registros)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
