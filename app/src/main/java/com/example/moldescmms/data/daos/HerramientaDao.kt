package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Herramienta
import kotlinx.coroutines.flow.Flow

@Dao
interface HerramientaDao {
    @Query("SELECT * FROM herramientas ORDER BY nombre ASC")
    fun getAll(): Flow<List<Herramienta>>
    
    @Query("SELECT * FROM herramientas WHERE id = :id")
    suspend fun getById(id: Long): Herramienta?
    
    @Query("SELECT * FROM herramientas WHERE categoria = :categoria ORDER BY nombre ASC")
    fun getByCategoria(categoria: String): Flow<List<Herramienta>>
    
    @Query("SELECT * FROM herramientas WHERE estadoAsignacion = :estado")
    fun getByEstadoAsignacion(estado: String): Flow<List<Herramienta>>
    
    @Query("SELECT * FROM herramientas WHERE cantidad < cantidadMinima")
    fun getBajoStock(): Flow<List<Herramienta>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(herramienta: Herramienta): Long
    
    @Update
    suspend fun update(herramienta: Herramienta)
    
    @Delete
    suspend fun delete(herramienta: Herramienta)
}
