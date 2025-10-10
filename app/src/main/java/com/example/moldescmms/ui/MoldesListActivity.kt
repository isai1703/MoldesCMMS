package com.example.moldescmms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase

class MoldesListActivity : AppCompatActivity() {
    
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moldes_list)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Lista de Moldes"
        
        database = AppDatabase.getDatabase(this)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
