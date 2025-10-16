package com.example.moldescmms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moldescmms.R

class EstadisticasEquipoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_equipo)
        supportActionBar?.title = "Estadísticas del Equipo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
