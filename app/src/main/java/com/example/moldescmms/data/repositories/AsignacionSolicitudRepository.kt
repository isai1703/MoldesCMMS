package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.AsignacionSolicitudDao
import com.example.moldescmms.data.entities.AsignacionSolicitud
import kotlinx.coroutines.flow.Flow

class AsignacionSolicitudRepository(private val asignacionSolicitudDao: AsignacionSolicitudDao) {
    
    val allAsignaciones: Flow<List<AsignacionSolicitud>> = asignacionSolicitudDao.getAllAsignaciones()
    
    fun getAsignacionById(id: Long): Flow<AsignacionSolicitud?> = 
        asignacionSolicitudDao.getAsignacionById(id)
    
    fun getAsignacionesByTecnico(tecnicoId: Long): Flow<List<AsignacionSolicitud>> = 
        asignacionSolicitudDao.getAsignacionesByTecnico(tecnicoId)
    
    fun getAsignacionesActivasByTecnico(tecnicoId: Long): Flow<List<AsignacionSolicitud>> = 
        asignacionSolicitudDao.getAsignacionesActivasByTecnico(tecnicoId)
    
    fun getAsignacionBySolicitud(solicitudId: Long): Flow<AsignacionSolicitud?> = 
        asignacionSolicitudDao.getAsignacionBySolicitud(solicitudId)
    
    fun getAsignacionesByEstado(estado: String): Flow<List<AsignacionSolicitud>> = 
        asignacionSolicitudDao.getAsignacionesByEstado(estado)
    
    fun getAsignacionesByTurno(turno: String): Flow<List<AsignacionSolicitud>> = 
        asignacionSolicitudDao.getAsignacionesByTurno(turno)
    
    suspend fun getCargaTrabajo(tecnicoId: Long): Int = 
        asignacionSolicitudDao.getCargaTrabajo(tecnicoId)
    
    suspend fun insert(asignacion: AsignacionSolicitud): Long = 
        asignacionSolicitudDao.insert(asignacion)
    
    suspend fun update(asignacion: AsignacionSolicitud) = 
        asignacionSolicitudDao.update(asignacion)
    
    suspend fun delete(asignacion: AsignacionSolicitud) = 
        asignacionSolicitudDao.delete(asignacion)
}
