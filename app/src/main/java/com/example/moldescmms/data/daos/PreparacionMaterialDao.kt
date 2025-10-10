package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.PreparacionMaterial
import kotlinx.coroutines.flow.Flow

@Dao
interface PreparacionMaterialDao {
    @Query("SELECT * FROM preparacion_material ORDER BY fechaPreparacion DESC")
    fun getAll(): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparacion_material WHERE id = :id")
    suspend fun getById(id: Long): PreparacionMaterial?
    
    @Query("SELECT * FROM preparacion_material WHERE materialEstasId = :materialEstasId ORDER BY fechaPreparacion DESC")
    fun getByMaterialEstas(materialEstasId: Long): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparacion_material WHERE productoId = :productoId ORDER BY fechaPreparacion DESC")
    fun getByProducto(productoId: Long): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparacion_material WHERE fechaPreparacion BETWEEN :inicio AND :fin ORDER BY fechaPreparacion DESC")
    fun getByRangoFecha(inicio: Long, fin: Long): Flow<List<PreparacionMaterial>>
    
    @Query("SELECT * FROM preparacion_material WHERE turno = :turno ORDER BY fechaPreparacion DESC")
    fun getByTurno(turno: String): Flow<List<PreparacionMaterial>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preparacion: PreparacionMaterial): Long
    
    @Update
    suspend fun update(preparacion: PreparacionMaterial)
    
    @Delete
    suspend fun delete(preparacion: PreparacionMaterial)
}
