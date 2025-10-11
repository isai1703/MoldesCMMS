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

class MoldesActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moldes)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Moldes"
        
        database = AppDatabase.getDatabase(this)
        
        val recyclerView = findViewById<RecyclerView>(R.id.rv_moldes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        findViewById<FloatingActionButton>(R.id.fab_add_molde).setOnClickListener {
            try {
                startActivity(Intent(this, MoldeFormActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Error al abrir formulario", Toast.LENGTH_SHORT).show()
            }
        }
        
        loadMoldes()
    }
    
    private fun loadMoldes() {
        lifecycleScope.launch {
            try {
                database.moldeDao().getAll().collect { moldes ->
                    // Por ahora solo mostrar en Toast
                    if (moldes.isEmpty()) {
                        Toast.makeText(this@MoldesActivity, "No hay moldes registrados", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MoldesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
