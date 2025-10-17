package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.TecnicoTaller

@Dao
interface TecnicoTallerDao {
    
    @Insert
    suspend fun insert(tecnico: TecnicoTaller): Long
    
    @Update
    suspend fun update(tecnico: TecnicoTaller)
    
    @Delete
    suspend fun delete(tecnico: TecnicoTaller)
    
    @Query("SELECT * FROM tecnicos_taller WHERE id = :id")
    suspend fun getById(id: Long): TecnicoTaller?
    
    @Query("SELECT * FROM tecnicos_taller")
    suspend fun getAll(): List<TecnicoTaller>
}
