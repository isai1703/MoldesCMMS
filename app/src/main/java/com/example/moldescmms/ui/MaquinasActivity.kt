package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MaquinasActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maquinas)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Máquinas"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_maquinas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        findViewById<FloatingActionButton>(R.id.fab_add_maquina).setOnClickListener {
            try {
                startActivity(Intent(this, MaquinaFormActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Error al abrir formulario", Toast.LENGTH_SHORT).show()
            }
        }
        
        loadMaquinas()
    }
    
    private fun loadMaquinas() {
        lifecycleScope.launch {
            try {
                database.maquinaDao().getAll().collect { maquinas ->
                    if (maquinas.isEmpty()) {
                        Toast.makeText(this@MaquinasActivity, "No hay máquinas registradas", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MaquinasActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
