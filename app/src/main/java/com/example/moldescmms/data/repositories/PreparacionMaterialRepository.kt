package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.PreparacionMaterialDao
import com.example.moldescmms.data.entities.PreparacionMaterial
import kotlinx.coroutines.flow.Flow

class PreparacionMaterialRepository(private val preparacionMaterialDao: PreparacionMaterialDao) {
    
    val allPreparaciones: Flow<List<PreparacionMaterial>> = preparacionMaterialDao.getAllPreparaciones()
    
    fun getPreparacionById(id: Long): Flow<PreparacionMaterial?> = 
        preparacionMaterialDao.getPreparacionById(id)
    
    fun getPreparacionesByMaterialista(materialistaId: Long): Flow<List<PreparacionMaterial>> = 
        preparacionMaterialDao.getPreparacionesByMaterialista(materialistaId)
    
    fun getPreparacionesByEstado(estado: String): Flow<List<PreparacionMaterial>> = 
        preparacionMaterialDao.getPreparacionesByEstado(estado)
    
    fun getPreparacionesByFecha(inicio: Long, fin: Long): Flow<List<PreparacionMaterial>> = 
        preparacionMaterialDao.getPreparacionesByFecha(inicio, fin)
    
    fun getPreparacionesByMaterialistaYFecha(materialistaId: Long, inicio: Long, fin: Long): Flow<List<PreparacionMaterial>> = 
        preparacionMaterialDao.getPreparacionesByMaterialistaYFecha(materialistaId, inicio, fin)
    
    suspend fun getEficienciaPromedio(materialistaId: Long): Double? = 
        preparacionMaterialDao.getEficienciaPromedio(materialistaId)
    
    suspend fun insert(preparacion: PreparacionMaterial): Long = 
        preparacionMaterialDao.insert(preparacion)
    
    suspend fun update(preparacion: PreparacionMaterial) = 
        preparacionMaterialDao.update(preparacion)
    
    suspend fun delete(preparacion: PreparacionMaterial) = 
        preparacionMaterialDao.delete(preparacion)
}
