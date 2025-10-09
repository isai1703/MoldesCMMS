package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.MoldeDao
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.flow.Flow

class MoldeRepository(private val moldeDao: MoldeDao) {
    
    fun getAllMoldes(): Flow<List<Molde>> = moldeDao.getAll()
    
    fun getMoldesByEstado(estado: String): Flow<List<Molde>> = moldeDao.getByEstado(estado)
    
    suspend fun getMoldeById(id: Long): Molde? = moldeDao.getById(id)
    
    suspend fun getMoldeByCodigo(codigo: String): Molde? = moldeDao.getByCodigo(codigo)
    
    suspend fun insertMolde(molde: Molde): Long = moldeDao.insert(molde)
    
    suspend fun updateMolde(molde: Molde) = moldeDao.update(molde)
    
    suspend fun deleteMolde(molde: Molde) = moldeDao.delete(molde)
    
    suspend fun getMoldesCount(): Int = moldeDao.getCount()
}
