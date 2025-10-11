package com.example.moldescmms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase

class InventarioActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Inventario y Compras"
        
        database = AppDatabase.getDatabase(this)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
