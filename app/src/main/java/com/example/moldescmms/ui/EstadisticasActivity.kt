package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import kotlinx.coroutines.launch

class EstadisticasActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Estadísticas"
        
        database = AppDatabase.getDatabase(this)
        
        loadEstadisticas()
    }
    
    private fun loadEstadisticas() {
        lifecycleScope.launch {
            val totalMoldes = database.moldeDao().getCount()
            findViewById<TextView>(R.id.tv_total_moldes)?.text = "Total Moldes: $totalMoldes"
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
