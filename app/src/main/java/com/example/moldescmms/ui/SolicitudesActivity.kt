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

class SolicitudesActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Solicitudes de Mantenimiento"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_solicitudes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        findViewById<FloatingActionButton>(R.id.fab_add_solicitud).setOnClickListener {
            try {
                startActivity(Intent(this, SolicitudMantenimientoFormActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Error al abrir formulario", Toast.LENGTH_SHORT).show()
            }
        }
        
        loadSolicitudes()
    }
    
    private fun loadSolicitudes() {
        lifecycleScope.launch {
            try {
                database.solicitudMantenimientoDao().getAll().collect { solicitudes ->
                    if (solicitudes.isEmpty()) {
                        Toast.makeText(this@SolicitudesActivity, "No hay solicitudes registradas", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@SolicitudesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
