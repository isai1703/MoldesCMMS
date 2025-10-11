package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SolicitudesActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Solicitudes de Mantenimiento"
        
        findViewById<FloatingActionButton>(R.id.fab_add_solicitud)?.setOnClickListener {
            startActivity(Intent(this, SolicitudMantenimientoFormActivity::class.java))
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
