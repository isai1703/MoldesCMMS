package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.MoldeRepository
import kotlinx.coroutines.launch

class EstadisticasTallerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_taller)

        supportActionBar?.title = "Estadísticas del Taller"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cargarEstadisticas()
    }

    private fun cargarEstadisticas() {
        // TODO: Implementar consultas reales a la base de datos
        findViewById<TextView>(R.id.tv_total_solicitudes).text = "Total Solicitudes: 0"
        findViewById<TextView>(R.id.tv_solicitudes_completadas).text = "Completadas: 0"
        findViewById<TextView>(R.id.tv_solicitudes_pendientes).text = "Pendientes: 0"
        findViewById<TextView>(R.id.tv_eficiencia_tecnico).text = "Eficiencia: 0%"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
