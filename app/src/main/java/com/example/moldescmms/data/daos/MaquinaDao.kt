package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Maquina
import kotlinx.coroutines.flow.Flow

@Dao
interface MaquinaDao {
    @Query("SELECT * FROM maquinas ORDER BY codigo ASC")
    fun getAll(): Flow<List<Maquina>>
    
    @Query("SELECT * FROM maquinas WHERE id = :id")
    suspend fun getById(id: Long): Maquina?
    
    @Query("SELECT * FROM maquinas WHERE codigo = :codigo")
    suspend fun getByCodigo(codigo: String): Maquina?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(maquina: Maquina): Long
    
    @Update
    suspend fun update(maquina: Maquina)
    
    @Delete
    suspend fun delete(maquina: Maquina)
    
    @Query("SELECT * FROM maquinas ORDER BY codigo ASC")
    suspend fun getAllMaquinasList(): List<Maquina>
}
