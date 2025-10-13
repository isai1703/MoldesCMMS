package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.AsignacionProduccionDao
import com.example.moldescmms.data.entities.AsignacionProduccion
import kotlinx.coroutines.flow.Flow

class AsignacionProduccionRepository(private val asignacionProduccionDao: AsignacionProduccionDao) {
    
    val allAsignaciones: Flow<List<AsignacionProduccion>> = asignacionProduccionDao.getAllAsignaciones()
    
    fun getAsignacionById(id: Long): Flow<AsignacionProduccion?> = 
        asignacionProduccionDao.getAsignacionById(id)
    
    fun getAsignacionesByOperador(operadorId: Long): Flow<List<AsignacionProduccion>> = 
        asignacionProduccionDao.getAsignacionesByOperador(operadorId)
    
    fun getAsignacionesByMaquina(maquinaId: Long): Flow<List<AsignacionProduccion>> = 
        asignacionProduccionDao.getAsignacionesByMaquina(maquinaId)
    
    fun getAsignacionesByMolde(moldeId: Long): Flow<List<AsignacionProduccion>> = 
        asignacionProduccionDao.getAsignacionesByMolde(moldeId)
    
    fun getAsignacionesByEstado(estado: String): Flow<List<AsignacionProduccion>> = 
        asignacionProduccionDao.getAsignacionesByEstado(estado)
    
    fun getAsignacionesByFecha(inicio: Long, fin: Long): Flow<List<AsignacionProduccion>> = 
        asignacionProduccionDao.getAsignacionesByFecha(inicio, fin)
    
    fun getAsignacionActivaOperador(operadorId: Long): Flow<AsignacionProduccion?> = 
        asignacionProduccionDao.getAsignacionActivaOperador(operadorId)
    
    suspend fun getEficienciaPromedioOperador(operadorId: Long): Double? = 
        asignacionProduccionDao.getEficienciaPromedioOperador(operadorId)
    
    suspend fun getTotalProduccionOperador(operadorId: Long): Int? = 
        asignacionProduccionDao.getTotalProduccionOperador(operadorId)
    
    suspend fun insert(asignacion: AsignacionProduccion): Long = 
        asignacionProduccionDao.insert(asignacion)
    
    suspend fun update(asignacion: AsignacionProduccion) = 
        asignacionProduccionDao.update(asignacion)
    
    suspend fun delete(asignacion: AsignacionProduccion) = 
        asignacionProduccionDao.delete(asignacion)
}
