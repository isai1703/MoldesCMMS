package com.example.moldescmms.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.repositories.AsignacionSolicitudRepository
import com.example.moldescmms.data.repositories.SolicitudMantenimientoRepository
import com.example.moldescmms.data.repositories.TecnicoTallerRepository
import com.example.moldescmms.ui.adapters.SolicitudPendienteAdapter
import kotlinx.coroutines.launch
import java.util.*

class SupervisorDashboardActivity : AppCompatActivity() {
    
    private lateinit var solicitudRepository: SolicitudMantenimientoRepository
    private lateinit var tecnicoRepository: TecnicoTallerRepository
    private lateinit var asignacionRepository: AsignacionSolicitudRepository
    private lateinit var adapter: SolicitudPendienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supervisor_dashboard)

        supportActionBar?.title = "Dashboard Supervisor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        solicitudRepository = SolicitudMantenimientoRepository(db.solicitudMantenimientoDao())
        tecnicoRepository = TecnicoTallerRepository(db.tecnicoTallerDao())
        asignacionRepository = AsignacionSolicitudRepository(db.asignacionSolicitudDao())

        setupUI()
        cargarDatos()
    }

    private fun setupUI() {
        // Botones principales
        findViewById<Button>(R.id.btn_solicitudes_pendientes).setOnClickListener {
            startActivity(Intent(this, SolicitudesPendientesActivity::class.java))
        }

        findViewById<Button>(R.id.btn_gestionar_auxiliares).setOnClickListener {
            startActivity(Intent(this, GestionarAuxiliaresActivity::class.java))
        }

        findViewById<Button>(R.id.btn_asignaciones_activas).setOnClickListener {
            startActivity(Intent(this, AsignacionesActivasActivity::class.java))
        }

        findViewById<Button>(R.id.btn_estadisticas_equipo).setOnClickListener {
            startActivity(Intent(this, EstadisticasEquipoActivity::class.java))
        }

        // RecyclerView para solicitudes urgentes
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_solicitudes_urgentes)
        adapter = SolicitudPendienteAdapter { solicitud ->
            val intent = Intent(this, AsignarSolicitudActivity::class.java)
            intent.putExtra("solicitud_id", solicitud.id)
            startActivity(intent)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun cargarDatos() {
        lifecycleScope.launch {
            // Turno actual
            val turnoActual = obtenerTurnoActual()
            findViewById<TextView>(R.id.tv_turno_actual).text = "Turno: $turnoActual"

            // Estadísticas
            solicitudRepository.getSolicitudesByEstado("Pendiente").collect { pendientes ->
                findViewById<TextView>(R.id.tv_solicitudes_pendientes).text = 
                    "${pendientes.size} Solicitudes Pendientes"
                
                // Filtrar solo urgentes para el RecyclerView
                val urgentes = pendientes.filter { it.prioridad == "Urgente" }
                adapter.submitList(urgentes)
            }

            asignacionRepository.getAsignacionesByEstado("En Proceso").collect { enProceso ->
                findViewById<TextView>(R.id.tv_trabajos_en_proceso).text = 
                    "${enProceso.size} Trabajos en Proceso"
            }

            tecnicoRepository.getAuxiliaresDisponibles().collect { auxiliares ->
                val disponibles = auxiliares.filter { it.solicitudesEnProceso < 3 }
                findViewById<TextView>(R.id.tv_auxiliares_disponibles).text = 
                    "${disponibles.size} Auxiliares Disponibles"
            }
        }
    }

    private fun obtenerTurnoActual(): String {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hora) {
            in 6..13 -> "Matutino (6:00-14:00)"
            in 14..21 -> "Vespertino (14:00-22:00)"
            else -> "Nocturno (22:00-6:00)"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        cargarDatos()
    }
}
