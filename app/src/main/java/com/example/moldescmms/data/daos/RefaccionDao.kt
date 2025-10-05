package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Refaccion
import kotlinx.coroutines.flow.Flow

@Dao
interface RefaccionDao {
    @Query("SELECT * FROM refacciones ORDER BY nombre ASC")
    fun getAll(): Flow<List<Refaccion>>
    
    @Query("SELECT * FROM refacciones WHERE id = :id")
    suspend fun getById(id: Long): Refaccion?
    
    @Query("SELECT * FROM refacciones WHERE categoria = :categoria ORDER BY nombre ASC")
    fun getByCategoria(categoria: String): Flow<List<Refaccion>>
    
    @Query("SELECT * FROM refacciones WHERE stockActual <= stockMinimo")
    fun getBajoStock(): Flow<List<Refaccion>>
    
    @Query("SELECT * FROM refacciones WHERE requiereReorden = 1")
    fun getRequierenReorden(): Flow<List<Refaccion>>
    
    @Query("SELECT * FROM refacciones WHERE nombre LIKE '%' || :query || '%' OR codigo LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Refaccion>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refaccion: Refaccion): Long
    
    @Update
    suspend fun update(refaccion: Refaccion)
    
    @Delete
    suspend fun delete(refaccion: Refaccion)
}
