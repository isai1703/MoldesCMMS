package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.MoldeDao
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.flow.Flow

class MoldeRepository(private val moldeDao: MoldeDao) {
    
    val allMoldes: Flow<List<Molde>> = moldeDao.getAll()
    
    suspend fun insert(molde: Molde): Long {
        return moldeDao.insert(molde)
    }
    
    suspend fun update(molde: Molde) {
        moldeDao.update(molde)
    }
    
    suspend fun delete(molde: Molde) {
        moldeDao.delete(molde)
    }
    
    suspend fun getById(id: Long): Molde? {
        return moldeDao.getById(id)
    }
    
    suspend fun getByCodigo(codigo: String): Molde? {
        return moldeDao.getByCodigo(codigo)
    }
    
    fun getByEstado(estado: String): Flow<List<Molde>> {
        return moldeDao.getByEstado(estado)
    }
    
    fun search(query: String): Flow<List<Molde>> {
        return moldeDao.search(query)
    }
    
    suspend fun getCount(): Int {
        return moldeDao.getCount()
    }
}
