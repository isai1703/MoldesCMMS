package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.RequerimientoInsumo
import kotlinx.coroutines.flow.Flow

@Dao
interface RequerimientoInsumoDao {
    @Query("SELECT * FROM requerimientos_insumos ORDER BY fechaCreacion DESC")
    fun getAll(): Flow<List<RequerimientoInsumo>>
    
    @Query("SELECT * FROM requerimientos_insumos WHERE id = :id")
    suspend fun getById(id: Long): RequerimientoInsumo?
    
    @Query("SELECT * FROM requerimientos_insumos WHERE estado = :estado ORDER BY prioridad DESC")
    fun getByEstado(estado: String): Flow<List<RequerimientoInsumo>>
    
    @Query("SELECT * FROM requerimientos_insumos WHERE solicitadoPor = :supervisor")
    fun getBySupervisor(supervisor: String): Flow<List<RequerimientoInsumo>>
    
    @Query("SELECT COUNT(*) FROM requerimientos_insumos WHERE estado = 'Pendiente'")
    suspend fun getPendientesCount(): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requerimiento: RequerimientoInsumo): Long
    
    @Update
    suspend fun update(requerimiento: RequerimientoInsumo)
}
