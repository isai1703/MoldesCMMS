package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.AsignacionSolicitud

@Dao
interface AsignacionSolicitudDao {
    
    @Insert
    suspend fun insert(asignacion: AsignacionSolicitud): Long
    
    @Update
    suspend fun update(asignacion: AsignacionSolicitud)
    
    @Delete
    suspend fun delete(asignacion: AsignacionSolicitud)
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE id = :id")
    suspend fun getById(id: Long): AsignacionSolicitud?
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE tecnicoId = :auxiliarId")
    suspend fun getByAuxiliarId(auxiliarId: Long): List<AsignacionSolicitud>
    
    @Query("SELECT * FROM asignaciones_solicitud")
    suspend fun getAll(): List<AsignacionSolicitud>
}
