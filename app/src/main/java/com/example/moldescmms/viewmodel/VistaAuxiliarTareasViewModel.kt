package com.example.moldescmms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.AsignacionSolicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class EstadisticasTareas(
    val pendientes: Int = 0,
    val enProceso: Int = 0,
    val completadas: Int = 0
)

class VistaAuxiliarTareasViewModel(application: Application) : AndroidViewModel(application) {
    
    private val db = AppDatabase.getDatabase(application)
    private val asignacionDao = db.asignacionSolicitudDao()
    
    private val _tareasAgrupadas = MutableStateFlow<List<AsignacionSolicitud>>(emptyList())
    val tareasAgrupadas: StateFlow<List<AsignacionSolicitud>> = _tareasAgrupadas
    
    private val _estadisticas = MutableStateFlow<EstadisticasTareas?>(null)
    val estadisticas: StateFlow<EstadisticasTareas?> = _estadisticas
    
    private var estadoFiltro = "PENDIENTE"
    private var auxiliarIdActual = -1L
    
    suspend fun cargarTareasAuxiliar(auxiliarId: Long) {
        auxiliarIdActual = auxiliarId
        viewModelScope.launch {
            val todasTareas = asignacionDao.obtenerPorAuxiliarId(auxiliarId)
            calcularEstadisticas(todasTareas)
            filtrarPorEstado(estadoFiltro)
        }
    }
    
    fun filtrarPorEstado(estado: String) {
        estadoFiltro = estado
        viewModelScope.launch {
            val todasTareas = asignacionDao.obtenerPorAuxiliarId(auxiliarIdActual)
            val filtradas = todasTareas.filter { it.estado == estado }
            _tareasAgrupadas.emit(filtradas)
            calcularEstadisticas(todasTareas)
        }
    }
    
    private fun calcularEstadisticas(tareas: List<AsignacionSolicitud>) {
        val stats = EstadisticasTareas(
            pendientes = tareas.count { it.estado == "PENDIENTE" },
            enProceso = tareas.count { it.estado == "EN_PROCESO" },
            completadas = tareas.count { it.estado == "COMPLETADA" }
        )
        viewModelScope.launch {
            _estadisticas.emit(stats)
        }
    }
}
