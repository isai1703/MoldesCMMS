package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Mantenimiento
import kotlinx.coroutines.flow.Flow

@Dao
interface MantenimientoDao {
    @Query("SELECT * FROM mantenimientos ORDER BY fechaProgramada DESC")
    fun getAll(): Flow<List<Mantenimiento>>
    
    @Query("SELECT * FROM mantenimientos WHERE moldeId = :moldeId ORDER BY fechaProgramada DESC")
    fun getByMolde(moldeId: Long): Flow<List<Mantenimiento>>
    
    @Query("SELECT * FROM mantenimientos WHERE id = :id")
    suspend fun getById(id: Long): Mantenimiento?
    
    @Query("SELECT * FROM mantenimientos WHERE estado = :estado ORDER BY fechaProgramada ASC")
    fun getByEstado(estado: String): Flow<List<Mantenimiento>>
    
    @Query("SELECT * FROM mantenimientos WHERE tipo = :tipo ORDER BY fechaProgramada DESC")
    fun getByTipo(tipo: String): Flow<List<Mantenimiento>>
    
    @Query("SELECT * FROM mantenimientos WHERE fechaProgramada BETWEEN :inicio AND :fin ORDER BY fechaProgramada ASC")
    fun getByRangoFechas(inicio: Long, fin: Long): Flow<List<Mantenimiento>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mantenimiento: Mantenimiento): Long
    
    @Update
    suspend fun update(mantenimiento: Mantenimiento)
    
    @Delete
    suspend fun delete(mantenimiento: Mantenimiento)
    
    @Query("SELECT COUNT(*) FROM mantenimientos WHERE estado = 'Pendiente'")
    suspend fun getPendientesCount(): Int
}
