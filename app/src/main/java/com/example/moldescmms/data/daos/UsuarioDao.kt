package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE activo = 1 ORDER BY nombre ASC")
    fun getAll(): Flow<List<Usuario>>
    
    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getById(id: Long): Usuario?
    
    @Query("SELECT * FROM usuarios WHERE numeroEmpleado = :numeroEmpleado AND password = :password AND activo = 1")
    suspend fun login(numeroEmpleado: String, password: String): Usuario?
    
    @Query("SELECT * FROM usuarios WHERE rol = :rol AND activo = 1")
    fun getByRol(rol: String): Flow<List<Usuario>>
    
    @Query("SELECT * FROM usuarios WHERE departamento = :departamento AND activo = 1")
    fun getByDepartamento(departamento: String): Flow<List<Usuario>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario): Long
    
    @Update
    suspend fun update(usuario: Usuario)
    
    @Delete
    suspend fun delete(usuario: Usuario)
}
