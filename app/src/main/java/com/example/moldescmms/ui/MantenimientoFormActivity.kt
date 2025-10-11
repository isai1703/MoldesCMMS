package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class MantenimientoFormActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento_form)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nuevo Mantenimiento"
        
        findViewById<Button>(R.id.btn_save_mant)?.setOnClickListener {
            Toast.makeText(this, "Función en desarrollo", Toast.LENGTH_SHORT).show()
        }
        
        findViewById<Button>(R.id.btn_cancel_mant)?.setOnClickListener {
            finish()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
