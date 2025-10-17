package com.example.moldescmms.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moldescmms.data.AppDatabase
import com.example.moldescmms.data.entities.AsignacionSolicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DetalleTrabajoUI(
    val id: Long,
    val solicitudMantenimientoId: Long,
    val moldeNombre: String,
    val estado: String,
    val descripcionSolicitud: String,
    val fechaAsignacion: Long,
    val supervisorNombre: String,
    val auxiliarNombre: String,
    val turnoPreferente: String,
    val notas: String,
    val notasTecnico: String = ""
)

class TrabajoDetalleViewModel(application: Application) : AndroidViewModel(application) {
    
    private val db = AppDatabase.getDatabase(application)
    private val asignacionDao = db.asignacionSolicitudDao()
    private val solicitudDao = db.solicitudMantenimientoDao()
    private val moldeDao = db.moldeDao()
    private val supervisorDao = db.tecnicoTallerDao()
    
    private val _asignacionData = MutableStateFlow<DetalleTrabajoUI?>(null)
    val asignacionData: StateFlow<DetalleTrabajoUI?> = _asignacionData
    
    private val _mensajeError = MutableStateFlow("")
    val mensajeError: StateFlow<String> = _mensajeError
    
    fun cargarDetalleAsignacion(asignacionId: Long) {
        viewModelScope.launch {
            try {
                val asignacion = asignacionDao.getById(asignacionId)
                asignacion?.let {
                    val solicitud = solicitudDao.getById(it.solicitudMantenimientoId)
                    val molde = moldeDao.getById(solicitud?.moldeId ?: 0L)
                    val supervisor = supervisorDao.getById(it.supervisorId)
                    val auxiliar = supervisorDao.getById(it.auxiliarId)
                    
                    val detalle = DetalleTrabajoUI(
                        id = it.id,
                        solicitudMantenimientoId = it.solicitudMantenimientoId,
                        moldeNombre = molde?.nombre ?: "Desconocido",
                        estado = it.estado,
                        descripcionSolicitud = solicitud?.descripcion ?: "",
                        fechaAsignacion = it.fechaAsignacion,
                        supervisorNombre = supervisor?.nombre ?: "Desconocido",
                        auxiliarNombre = auxiliar?.nombre ?: "Desconocido",
                        turnoPreferente = it.turnoPreferente,
                        notas = it.notas,
                        notasTecnico = it.notasTecnico
                    )
                    _asignacionData.emit(detalle)
                }
            } catch (e: Exception) {
                _mensajeError.emit("Error al cargar: ${e.message}")
            }
        }
    }
    
    fun cambiarEstado(asignacionId: Long, nuevoEstado: String) {
        viewModelScope.launch {
            try {
                val asignacion = asignacionDao.getById(asignacionId)
                asignacion?.let {
                    val actualizado = it.copy(
                        estado = nuevoEstado,
                        fechaActualizacion = System.currentTimeMillis()
                    )
                    asignacionDao.update(actualizado)
                    cargarDetalleAsignacion(asignacionId)
                }
            } catch (e: Exception) {
                _mensajeError.emit("Error al cambiar estado: ${e.message}")
            }
        }
    }
    
    fun guardarNotasTecnico(asignacionId: Long, notas: String) {
        viewModelScope.launch {
            try {
                val asignacion = asignacionDao.getById(asignacionId)
                asignacion?.let {
                    val actualizado = it.copy(
                        notasTecnico = notas,
                        fechaActualizacion = System.currentTimeMillis()
                    )
                    asignacionDao.update(actualizado)
                    cargarDetalleAsignacion(asignacionId)
                }
            } catch (e: Exception) {
                _mensajeError.emit("Error al guardar notas: ${e.message}")
            }
        }
    }
}
