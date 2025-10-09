package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Operador
import kotlinx.coroutines.flow.Flow

@Dao
interface OperadorDao {
    @Query("SELECT * FROM operadores WHERE activo = 1 ORDER BY nombreCompleto ASC")
    fun getAllActivos(): Flow<List<Operador>>
    
    @Query("SELECT * FROM operadores WHERE id = :id")
    suspend fun getById(id: Long): Operador?
    
    @Query("SELECT * FROM operadores WHERE departamento = :departamento AND activo = 1")
    fun getByDepartamento(departamento: String): Flow<List<Operador>>
    
    @Query("SELECT * FROM operadores WHERE turno = :turno AND activo = 1")
    fun getByTurno(turno: String): Flow<List<Operador>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operador: Operador): Long
    
    @Update
    suspend fun update(operador: Operador)
    
    @Delete
    suspend fun delete(operador: Operador)
}
