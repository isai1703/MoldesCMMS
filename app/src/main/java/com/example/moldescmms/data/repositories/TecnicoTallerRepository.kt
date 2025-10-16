package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.TecnicoTallerDao
import com.example.moldescmms.data.entities.TecnicoTaller
import kotlinx.coroutines.flow.Flow

class TecnicoTallerRepository(private val tecnicoTallerDao: TecnicoTallerDao) {
    
    val allTecnicos: Flow<List<TecnicoTaller>> = tecnicoTallerDao.getAllTecnicos()
    
    fun getTecnicoById(id: Long): Flow<TecnicoTaller?> = tecnicoTallerDao.getTecnicoById(id)
    
    fun getTecnicosByRol(rol: String): Flow<List<TecnicoTaller>> = tecnicoTallerDao.getTecnicosByRol(rol)
    
    fun getAuxiliaresDisponibles(): Flow<List<TecnicoTaller>> = tecnicoTallerDao.getAuxiliaresDisponibles()
    
    suspend fun getTecnicoByNumeroEmpleado(numeroEmpleado: String): TecnicoTaller? = 
        tecnicoTallerDao.getTecnicoByNumeroEmpleado(numeroEmpleado)
    
    suspend fun insert(tecnico: TecnicoTaller): Long = tecnicoTallerDao.insert(tecnico)
    
    suspend fun update(tecnico: TecnicoTaller) = tecnicoTallerDao.update(tecnico)
    
    suspend fun delete(tecnico: TecnicoTaller) = tecnicoTallerDao.delete(tecnico)
    
    suspend fun incrementarSolicitudesEnProceso(tecnicoId: Long) = 
        tecnicoTallerDao.incrementarSolicitudesEnProceso(tecnicoId)
    
    suspend fun completarSolicitud(tecnicoId: Long) = 
        tecnicoTallerDao.completarSolicitud(tecnicoId)
}
