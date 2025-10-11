package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.MantenimientoMaquina
import kotlinx.coroutines.flow.Flow

@Dao
interface MantenimientoMaquinaDao {
    @Query("SELECT * FROM mantenimientos_maquinas ORDER BY fechaProgramada DESC")
    fun getAll(): Flow<List<MantenimientoMaquina>>
    
    @Query("SELECT * FROM mantenimientos_maquinas WHERE id = :id")
    suspend fun getById(id: Long): MantenimientoMaquina?
    
    @Query("SELECT * FROM mantenimientos_maquinas WHERE maquinaId = :maquinaId ORDER BY fechaProgramada DESC")
    fun getByMaquina(maquinaId: Long): Flow<List<MantenimientoMaquina>>
    
    @Query("SELECT * FROM mantenimientos_maquinas WHERE estado = :estado ORDER BY fechaProgramada DESC")
    fun getByEstado(estado: String): Flow<List<MantenimientoMaquina>>
    
    @Query("SELECT * FROM mantenimientos_maquinas WHERE tipo = :tipo ORDER BY fechaProgramada DESC")
    fun getByTipo(tipo: String): Flow<List<MantenimientoMaquina>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mantenimiento: MantenimientoMaquina): Long
    
    @Update
    suspend fun update(mantenimiento: MantenimientoMaquina)
    
    @Delete
    suspend fun delete(mantenimiento: MantenimientoMaquina)
}
