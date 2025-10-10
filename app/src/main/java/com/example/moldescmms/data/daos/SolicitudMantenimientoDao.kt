package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.SolicitudMantenimiento
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudMantenimientoDao {
    @Query("SELECT * FROM solicitudes_mantenimiento ORDER BY fechaSolicitud DESC")
    fun getAll(): Flow<List<SolicitudMantenimiento>>
    
    @Query("SELECT * FROM solicitudes_mantenimiento WHERE id = :id")
    suspend fun getById(id: Long): SolicitudMantenimiento?
    
    @Query("SELECT * FROM solicitudes_mantenimiento WHERE estado = :estado ORDER BY fechaSolicitud DESC")
    fun getByEstado(estado: String): Flow<List<SolicitudMantenimiento>>
    
    @Query("SELECT * FROM solicitudes_mantenimiento WHERE departamentoOrigen = :departamento ORDER BY fechaSolicitud DESC")
    fun getByDepartamento(departamento: String): Flow<List<SolicitudMantenimiento>>
    
    @Query("SELECT * FROM solicitudes_mantenimiento WHERE prioridad = :prioridad ORDER BY fechaSolicitud DESC")
    fun getByPrioridad(prioridad: String): Flow<List<SolicitudMantenimiento>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(solicitud: SolicitudMantenimiento): Long
    
    @Update
    suspend fun update(solicitud: SolicitudMantenimiento)
    
    @Delete
    suspend fun delete(solicitud: SolicitudMantenimiento)
}
