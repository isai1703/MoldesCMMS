package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): Usuario?
    
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): Usuario?
    
    @Query("SELECT * FROM usuarios WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): Usuario?
    
    @Query("SELECT * FROM usuarios WHERE activo = 1 ORDER BY nombreCompleto ASC")
    fun getAllActivos(): Flow<List<Usuario>>
    
    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getById(id: Long): Usuario?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario): Long
    
    @Update
    suspend fun update(usuario: Usuario)
    
    @Delete
    suspend fun delete(usuario: Usuario)
    
    @Query("UPDATE usuarios SET ultimoAcceso = :timestamp WHERE id = :id")
    suspend fun updateUltimoAcceso(id: Long, timestamp: Long)
}
