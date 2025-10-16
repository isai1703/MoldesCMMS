package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.AsignacionSolicitud
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignacionSolicitudDao {
    @Query("SELECT * FROM asignaciones_solicitud ORDER BY fechaAsignacion DESC")
    fun getAllAsignaciones(): Flow<List<AsignacionSolicitud>>
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE id = :id")
    fun getAsignacionById(id: Long): Flow<AsignacionSolicitud?>
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE tecnicoId = :tecnicoId ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByTecnico(tecnicoId: Long): Flow<List<AsignacionSolicitud>>
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE tecnicoId = :tecnicoId AND estado IN ('Asignada', 'En Proceso') ORDER BY prioridad DESC, fechaAsignacion ASC")
    fun getAsignacionesActivasByTecnico(tecnicoId: Long): Flow<List<AsignacionSolicitud>>
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE solicitudId = :solicitudId")
    fun getAsignacionBySolicitud(solicitudId: Long): Flow<AsignacionSolicitud?>
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE estado = :estado ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByEstado(estado: String): Flow<List<AsignacionSolicitud>>
    
    @Query("SELECT * FROM asignaciones_solicitud WHERE turnoAsignado = :turno AND estado IN ('Asignada', 'En Proceso') ORDER BY prioridad DESC")
    fun getAsignacionesByTurno(turno: String): Flow<List<AsignacionSolicitud>>
    
    @Query("SELECT COUNT(*) FROM asignaciones_solicitud WHERE tecnicoId = :tecnicoId AND estado IN ('Asignada', 'En Proceso')")
    suspend fun getCargaTrabajo(tecnicoId: Long): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asignacion: AsignacionSolicitud): Long
    
    @Update
    suspend fun update(asignacion: AsignacionSolicitud)
    
    @Delete
    suspend fun delete(asignacion: AsignacionSolicitud)
}
