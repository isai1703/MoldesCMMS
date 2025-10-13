package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.AsignacionProduccion
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignacionProduccionDao {
    @Query("SELECT * FROM asignaciones_produccion ORDER BY fechaAsignacion DESC")
    fun getAllAsignaciones(): Flow<List<AsignacionProduccion>>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE id = :id")
    fun getAsignacionById(id: Long): Flow<AsignacionProduccion?>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE operadorId = :operadorId ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByOperador(operadorId: Long): Flow<List<AsignacionProduccion>>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE maquinaId = :maquinaId ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByMaquina(maquinaId: Long): Flow<List<AsignacionProduccion>>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE moldeId = :moldeId ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByMolde(moldeId: Long): Flow<List<AsignacionProduccion>>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE estado = :estado ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByEstado(estado: String): Flow<List<AsignacionProduccion>>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE fechaAsignacion BETWEEN :inicio AND :fin ORDER BY fechaAsignacion DESC")
    fun getAsignacionesByFecha(inicio: Long, fin: Long): Flow<List<AsignacionProduccion>>
    
    @Query("SELECT * FROM asignaciones_produccion WHERE operadorId = :operadorId AND estado = 'En Proceso'")
    fun getAsignacionActivaOperador(operadorId: Long): Flow<AsignacionProduccion?>
    
    @Query("SELECT AVG(porcentajeEficiencia) FROM asignaciones_produccion WHERE operadorId = :operadorId AND estado = 'Completada'")
    suspend fun getEficienciaPromedioOperador(operadorId: Long): Double?
    
    @Query("SELECT SUM(cantidadProducida) FROM asignaciones_produccion WHERE operadorId = :operadorId AND estado = 'Completada'")
    suspend fun getTotalProduccionOperador(operadorId: Long): Int?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asignacion: AsignacionProduccion): Long
    
    @Update
    suspend fun update(asignacion: AsignacionProduccion)
    
    @Delete
    suspend fun delete(asignacion: AsignacionProduccion)
}
