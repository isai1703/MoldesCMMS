package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.SolicitudMantenimientoDao
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import kotlinx.coroutines.flow.Flow

class SolicitudMantenimientoRepository(private val solicitudMantenimientoDao: SolicitudMantenimientoDao) {
    
    val allSolicitudes: Flow<List<SolicitudMantenimiento>> = solicitudMantenimientoDao.getAll()
    
    suspend fun getSolicitudById(id: Long): SolicitudMantenimiento? = 
        solicitudMantenimientoDao.getById(id)
    
    fun getSolicitudesByEstado(estado: String): Flow<List<SolicitudMantenimiento>> = 
        solicitudMantenimientoDao.getByEstado(estado)
    
    fun getSolicitudesByDepartamento(departamento: String): Flow<List<SolicitudMantenimiento>> = 
        solicitudMantenimientoDao.getByDepartamento(departamento)
    
    fun getSolicitudesByPrioridad(prioridad: String): Flow<List<SolicitudMantenimiento>> = 
        solicitudMantenimientoDao.getByPrioridad(prioridad)
    
    suspend fun insert(solicitud: SolicitudMantenimiento): Long = 
        solicitudMantenimientoDao.insert(solicitud)
    
    suspend fun update(solicitud: SolicitudMantenimiento) = 
        solicitudMantenimientoDao.update(solicitud)
    
    suspend fun delete(solicitud: SolicitudMantenimiento) = 
        solicitudMantenimientoDao.delete(solicitud)
}
