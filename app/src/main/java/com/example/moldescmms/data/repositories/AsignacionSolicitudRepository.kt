package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.AsignacionSolicitudDao
import com.example.moldescmms.data.entities.AsignacionSolicitud

class AsignacionSolicitudRepository(private val dao: AsignacionSolicitudDao) {
    
    suspend fun getAll() = dao.getAll()
    suspend fun getById(id: Long) = dao.getById(id)
    suspend fun getByAuxiliarId(auxiliarId: Long) = dao.getByAuxiliarId(auxiliarId)
    suspend fun insert(asignacion: AsignacionSolicitud) = dao.insert(asignacion)
    suspend fun update(asignacion: AsignacionSolicitud) = dao.update(asignacion)
    suspend fun delete(asignacion: AsignacionSolicitud) = dao.delete(asignacion)
}
