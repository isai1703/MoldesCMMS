package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.RefaccionMaquina
import kotlinx.coroutines.flow.Flow

@Dao
interface RefaccionMaquinaDao {
    @Query("SELECT * FROM refacciones_maquinas ORDER BY nombre ASC")
    fun getAll(): Flow<List<RefaccionMaquina>>
    
    @Query("SELECT * FROM refacciones_maquinas WHERE id = :id")
    suspend fun getById(id: Long): RefaccionMaquina?
    
    @Query("SELECT * FROM refacciones_maquinas WHERE modeloMaquina = :modelo OR modelosCompatibles LIKE '%' || :modelo || '%'")
    fun getByModelo(modelo: String): Flow<List<RefaccionMaquina>>
    
    @Query("SELECT * FROM refacciones_maquinas WHERE stockActual <= stockMinimo")
    fun getBajoStock(): Flow<List<RefaccionMaquina>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refaccion: RefaccionMaquina): Long
    
    @Update
    suspend fun update(refaccion: RefaccionMaquina)
}
