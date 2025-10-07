package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Maquina
import kotlinx.coroutines.flow.Flow

@Dao
interface MaquinaDao {
    @Query("SELECT * FROM maquinas ORDER BY nombre ASC")
    fun getAll(): Flow<List<Maquina>>
    
    @Query("SELECT * FROM maquinas WHERE id = :id")
    suspend fun getById(id: Long): Maquina?
    
    @Query("SELECT * FROM maquinas WHERE departamentoId = :departamentoId")
    fun getByDepartamento(departamentoId: Long): Flow<List<Maquina>>
    
    @Query("SELECT * FROM maquinas WHERE estado = :estado")
    fun getByEstado(estado: String): Flow<List<Maquina>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(maquina: Maquina): Long
    
    @Update
    suspend fun update(maquina: Maquina)
    
    @Delete
    suspend fun delete(maquina: Maquina)
}
