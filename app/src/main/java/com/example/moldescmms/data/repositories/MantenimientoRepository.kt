package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.MantenimientoDao
import com.example.moldescmms.data.entities.Mantenimiento
import kotlinx.coroutines.flow.Flow

class MantenimientoRepository(private val mantenimientoDao: MantenimientoDao) {
    
    val allMantenimientos: Flow<List<Mantenimiento>> = mantenimientoDao.getAll()
    
    suspend fun insert(mantenimiento: Mantenimiento): Long {
        return mantenimientoDao.insert(mantenimiento)
    }
    
    suspend fun update(mantenimiento: Mantenimiento) {
        mantenimientoDao.update(mantenimiento)
    }
    
    suspend fun delete(mantenimiento: Mantenimiento) {
        mantenimientoDao.delete(mantenimiento)
    }
    
    fun getByMolde(moldeId: Long): Flow<List<Mantenimiento>> {
        return mantenimientoDao.getByMolde(moldeId)
    }
    
    fun getByEstado(estado: String): Flow<List<Mantenimiento>> {
        return mantenimientoDao.getByEstado(estado)
    }
    
    fun getByTipo(tipo: String): Flow<List<Mantenimiento>> {
        return mantenimientoDao.getByTipo(tipo)
    }
    
    suspend fun getPendientesCount(): Int {
        return mantenimientoDao.getPendientesCount()
    }
}
