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

class MantenimientosActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimientos)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mantenimientos"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_mantenimientos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        findViewById<FloatingActionButton>(R.id.fab_add_mantenimiento).setOnClickListener {
            try {
                startActivity(Intent(this, MantenimientoFormActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Error al abrir formulario", Toast.LENGTH_SHORT).show()
            }
        }
        
        loadMantenimientos()
    }
    
    private fun loadMantenimientos() {
        lifecycleScope.launch {
            try {
                database.mantenimientoDao().getAll().collect { mantenimientos ->
                    if (mantenimientos.isEmpty()) {
                        Toast.makeText(this@MantenimientosActivity, "No hay mantenimientos registrados", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MantenimientosActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
