package com.example.moldescmms.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moldescmms.R
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.AsignacionSolicitud
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import com.example.moldescmms.data.entities.TecnicoTaller
import com.example.moldescmms.data.repositories.AsignacionSolicitudRepository
import com.example.moldescmms.data.repositories.SolicitudMantenimientoRepository
import com.example.moldescmms.data.repositories.TecnicoTallerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class AsignarSolicitudActivity : AppCompatActivity() {
    
    private lateinit var solicitudRepository: SolicitudMantenimientoRepository
    private lateinit var tecnicoRepository: TecnicoTallerRepository
    private lateinit var asignacionRepository: AsignacionSolicitudRepository
    
    private var solicitudId: Long = 0
    private var solicitud: SolicitudMantenimiento? = null
    private var auxiliaresDisponibles: List<TecnicoTaller> = emptyList()
    private var tecnicoSeleccionado: TecnicoTaller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_solicitud)

        supportActionBar?.title = "Asignar Solicitud"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = AppDatabase.getDatabase(applicationContext)
        solicitudRepository = SolicitudMantenimientoRepository(db.solicitudMantenimientoDao())
        tecnicoRepository = TecnicoTallerRepository(db.tecnicoTallerDao())
        asignacionRepository = AsignacionSolicitudRepository(db.asignacionSolicitudDao())

        solicitudId = intent.getLongExtra("solicitud_id", 0)

        cargarDatos()
        setupListeners()
    }

    private fun cargarDatos() {
        lifecycleScope.launch {
            // Cargar solicitud
            solicitud = solicitudRepository.getSolicitudById(solicitudId).first()
            solicitud?.let {
                findViewById<TextView>(R.id.tv_solicitud_info).text = 
                    "Solicitud #${it.id} - ${it.prioridad}"
            }

            // Cargar auxiliares disponibles
            auxiliaresDisponibles = tecnicoRepository.getAuxiliaresDisponibles().first()
            
            // Generar sugerencias automáticas
            val sugerencias = generarSugerenciasInteligentes()
            mostrarSugerencias(sugerencias)
            
            // Llenar spinner con todos los auxiliares
            val nombres = auxiliaresDisponibles.map { "${it.nombre} (${it.solicitudesEnProceso} tareas)" }
            val spinnerAuxiliar = findViewById<Spinner>(R.id.spinner_auxiliar)
            spinnerAuxiliar.adapter = ArrayAdapter(this@AsignarSolicitudActivity, 
                android.R.layout.simple_spinner_dropdown_item, nombres)
        }
    }

    private fun generarSugerenciasInteligentes(): List<Pair<TecnicoTaller, Int>> {
        val turnoActual = obtenerTurnoActual()
        val solicitudActual = solicitud ?: return emptyList()
        
        // Algoritmo de puntuación
        return auxiliaresDisponibles.map { tecnico ->
            var puntuacion = 100
            
            // Factor 1: Carga de trabajo (más peso)
            puntuacion -= (tecnico.solicitudesEnProceso * 25)
            
            // Factor 2: Turno preferente
            if (tecnico.turnoPreferente == turnoActual) puntuacion += 20
            
            // Factor 3: Especialidad (si la solicitud requiere algo específico)
            // Aquí podrías agregar lógica según el tipo de mantenimiento
            
            // Factor 4: Historial de desempeño
            puntuacion += (tecnico.calificacionPromedio * 10).toInt()
            
            // Factor 5: Tiempo promedio de completado
            if (tecnico.promedioTiempoCompletado < 4.0) puntuacion += 10
            
            Pair(tecnico, puntuacion)
        }.sortedByDescending { it.second }
    }

    private fun mostrarSugerencias(sugerencias: List<Pair<TecnicoTaller, Int>>) {
        val layoutSugerencias = findViewById<LinearLayout>(R.id.layout_sugerencias)
        layoutSugerencias.removeAllViews()
        
        sugerencias.take(3).forEachIndexed { index, (tecnico, puntuacion) ->
            val cardView = layoutInflater.inflate(R.layout.item_sugerencia_auxiliar, layoutSugerencias, false)
            
            cardView.findViewById<TextView>(R.id.tv_posicion).text = "${index + 1}°"
            cardView.findViewById<TextView>(R.id.tv_nombre).text = tecnico.nombre
            cardView.findViewById<TextView>(R.id.tv_info).text = 
                "Tareas: ${tecnico.solicitudesEnProceso} | Puntuación: $puntuacion"
            cardView.findViewById<TextView>(R.id.tv_turno).text = "Turno: ${tecnico.turnoPreferente}"
            
            cardView.setOnClickListener {
                tecnicoSeleccionado = tecnico
                Toast.makeText(this, "Seleccionado: ${tecnico.nombre}", Toast.LENGTH_SHORT).show()
            }
            
            layoutSugerencias.addView(cardView)
        }
    }

    private fun setupListeners() {
        findViewById<Button>(R.id.btn_asignar_sugerido).setOnClickListener {
            val primerSugerido = generarSugerenciasInteligentes().firstOrNull()?.first
            if (primerSugerido != null) {
                asignarSolicitud(primerSugerido)
            }
        }

        findViewById<Button>(R.id.btn_asignar_manual).setOnClickListener {
            val posicion = findViewById<Spinner>(R.id.spinner_auxiliar).selectedItemPosition
            if (posicion >= 0 && posicion < auxiliaresDisponibles.size) {
                asignarSolicitud(auxiliaresDisponibles[posicion])
            }
        }
    }

    private fun asignarSolicitud(tecnico: TecnicoTaller) {
        lifecycleScope.launch {
            val turno = obtenerTurnoActual()
            val notas = findViewById<EditText>(R.id.et_notas_asignacion).text.toString()
            
            val asignacion = AsignacionSolicitud(
                solicitudId = solicitudId,
                tecnicoId = tecnico.id,
                tecnicoNombre = tecnico.nombre,
                asignadoPor = "Supervisor", // Aquí deberías usar el nombre del supervisor logueado
                turnoAsignado = turno,
                prioridad = solicitud?.prioridad ?: "Media",
                notasAsignacion = notas
            )
            
            asignacionRepository.insert(asignacion)
            tecnicoRepository.incrementarSolicitudesEnProceso(tecnico.id)
            
            // Actualizar estado de la solicitud
            solicitud?.let {
                val solicitudActualizada = it.copy(estado = "Asignada")
                solicitudRepository.update(solicitudActualizada)
            }
            
            Toast.makeText(this@AsignarSolicitudActivity, 
                "Solicitud asignada a ${tecnico.nombre}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun obtenerTurnoActual(): String {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hora) {
            in 6..13 -> "Matutino"
            in 14..21 -> "Vespertino"
            else -> "Nocturno"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
