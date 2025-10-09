package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Molde
import kotlinx.coroutines.flow.Flow

@Dao
interface MoldeDao {
    @Query("SELECT * FROM moldes ORDER BY codigo ASC")
    fun getAll(): Flow<List<Molde>>
    
    @Query("SELECT * FROM moldes WHERE id = :id")
    suspend fun getById(id: Long): Molde?
    
    @Query("SELECT * FROM moldes WHERE codigo = :codigo")
    suspend fun getByCodigo(codigo: String): Molde?
    
    @Query("SELECT * FROM moldes WHERE estado = :estado")
    fun getByEstado(estado: String): Flow<List<Molde>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(molde: Molde): Long
    
    @Update
    suspend fun update(molde: Molde)
    
    @Delete
    suspend fun delete(molde: Molde)
    
    @Query("SELECT COUNT(*) FROM moldes")
    suspend fun getCount(): Int
    
    @Query("SELECT * FROM moldes ORDER BY codigo ASC")
    suspend fun getAllMoldesList(): List<Molde>
}
