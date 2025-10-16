package com.example.moldescmms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class AsignacionesActivasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignaciones_activas)
        supportActionBar?.title = "Asignaciones Activas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
