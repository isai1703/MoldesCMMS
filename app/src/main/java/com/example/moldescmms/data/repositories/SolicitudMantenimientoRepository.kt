package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.SolicitudMantenimientoDao
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import kotlinx.coroutines.flow.Flow

class SolicitudMantenimientoRepository(private val solicitudMantenimientoDao: SolicitudMantenimientoDao) {
    
    val allSolicitudes: Flow<List<SolicitudMantenimiento>> = solicitudMantenimientoDao.getAllSolicitudes()
    
    fun getSolicitudById(id: Long): Flow<SolicitudMantenimiento?> = 
        solicitudMantenimientoDao.getSolicitudById(id)
    
    fun getSolicitudesByEstado(estado: String): Flow<List<SolicitudMantenimiento>> = 
        solicitudMantenimientoDao.getSolicitudesByEstado(estado)
    
    fun getSolicitudesByPrioridad(prioridad: String): Flow<List<SolicitudMantenimiento>> = 
        solicitudMantenimientoDao.getSolicitudesByPrioridad(prioridad)
    
    fun getSolicitudesByFecha(inicio: Long, fin: Long): Flow<List<SolicitudMantenimiento>> = 
        solicitudMantenimientoDao.getSolicitudesByFecha(inicio, fin)
    
    suspend fun insert(solicitud: SolicitudMantenimiento): Long = 
        solicitudMantenimientoDao.insert(solicitud)
    
    suspend fun update(solicitud: SolicitudMantenimiento) = 
        solicitudMantenimientoDao.update(solicitud)
    
    suspend fun delete(solicitud: SolicitudMantenimiento) = 
        solicitudMantenimientoDao.delete(solicitud)
}
