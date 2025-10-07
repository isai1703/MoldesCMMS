package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Departamento
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartamentoDao {
    @Query("SELECT * FROM departamentos WHERE activo = 1 ORDER BY nombre ASC")
    fun getAllActivos(): Flow<List<Departamento>>
    
    @Query("SELECT * FROM departamentos WHERE id = :id")
    suspend fun getById(id: Long): Departamento?
    
    @Query("SELECT * FROM departamentos WHERE tipo = :tipo")
    fun getByTipo(tipo: String): Flow<List<Departamento>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(departamento: Departamento): Long
    
    @Update
    suspend fun update(departamento: Departamento)
    
    @Delete
    suspend fun delete(departamento: Departamento)
}
