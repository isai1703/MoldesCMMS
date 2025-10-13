package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.PreparacionMaterial
import kotlinx.coroutines.flow.Flow

@Dao
interface PreparacionMaterialDao {
    @Query("SELECT * FROM preparaciones_material ORDER BY fechaPreparacion DESC")
    fun getAllPreparaciones(): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparaciones_material WHERE id = :id")
    fun getPreparacionById(id: Long): Flow<PreparacionMaterial?>
    
    @Query("SELECT * FROM preparaciones_material WHERE materialistaId = :materialistaId ORDER BY fechaPreparacion DESC")
    fun getPreparacionesByMaterialista(materialistaId: Long): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparaciones_material WHERE estado = :estado ORDER BY fechaPreparacion DESC")
    fun getPreparacionesByEstado(estado: String): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparaciones_material WHERE fechaPreparacion BETWEEN :inicio AND :fin ORDER BY fechaPreparacion DESC")
    fun getPreparacionesByFecha(inicio: Long, fin: Long): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparaciones_material WHERE materialistaId = :materialistaId AND fechaPreparacion BETWEEN :inicio AND :fin")
    fun getPreparacionesByMaterialistaYFecha(materialistaId: Long, inicio: Long, fin: Long): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT AVG(porcentajeEficiencia) FROM preparaciones_material WHERE materialistaId = :materialistaId AND estado = 'Completada'")
    suspend fun getEficienciaPromedio(materialistaId: Long): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preparacion: PreparacionMaterial): Long
    
    @Update
    suspend fun update(preparacion: PreparacionMaterial)
    
    @Delete
    suspend fun delete(preparacion: PreparacionMaterial)
}
