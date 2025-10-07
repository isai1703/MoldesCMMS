package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos WHERE activo = 1 ORDER BY nombre ASC")
    fun getAllActivos(): Flow<List<Producto>>
    
    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getById(id: Long): Producto?
    
    @Query("SELECT * FROM productos WHERE tipo = :tipo")
    fun getByTipo(tipo: String): Flow<List<Producto>>
    
    @Query("SELECT * FROM productos WHERE stockActual <= stockMinimo")
    fun getBajoStock(): Flow<List<Producto>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto): Long
    
    @Update
    suspend fun update(producto: Producto)
    
    @Delete
    suspend fun delete(producto: Producto)
}
