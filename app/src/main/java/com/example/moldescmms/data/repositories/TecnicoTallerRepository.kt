package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.TecnicoTallerDao
import com.example.moldescmms.data.entities.TecnicoTaller

class TecnicoTallerRepository(private val dao: TecnicoTallerDao) {
    
    suspend fun getAll() = dao.getAll()
    suspend fun getById(id: Long) = dao.getById(id)
    suspend fun insert(tecnico: TecnicoTaller) = dao.insert(tecnico)
    suspend fun update(tecnico: TecnicoTaller) = dao.update(tecnico)
    suspend fun delete(tecnico: TecnicoTaller) = dao.delete(tecnico)
}
