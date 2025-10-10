package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MantenimientosActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimientos)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mantenimientos"
        
        database = AppDatabase.getDatabase(this)
        
        findViewById<FloatingActionButton>(R.id.fab_add_mantenimiento)?.setOnClickListener {
            startActivity(Intent(this, MantenimientoFormActivity::class.java))
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
