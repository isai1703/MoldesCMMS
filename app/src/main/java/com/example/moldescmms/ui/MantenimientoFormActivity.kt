package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.launch

class MantenimientoFormActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    private val moldes = mutableListOf<Molde>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_form)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nuevo Mantenimiento"
        
        database = AppDatabase.getDatabase(this)
        
        loadMoldes()
    }
    
    private fun loadMoldes() {
        lifecycleScope.launch {
            moldes.clear()
            moldes.addAll(database.moldeDao().getAllMoldesList())
            
            val moldesNames = moldes.map { "${it.codigo} - ${it.nombre}" }
            val adapter = ArrayAdapter(this@MantenimientoFormActivity,
                android.R.layout.simple_spinner_item, moldesNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.sp_molde)?.adapter = adapter
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
