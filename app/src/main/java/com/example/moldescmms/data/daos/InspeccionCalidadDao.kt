package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.InspeccionCalidad
import kotlinx.coroutines.flow.Flow

@Dao
interface InspeccionCalidadDao {
    @Query("SELECT * FROM inspecciones_calidad ORDER BY fechaInspeccion DESC")
    fun getAll(): Flow<List<InspeccionCalidad>>
    
    @Query("SELECT * FROM inspecciones_calidad WHERE productoId = :productoId ORDER BY fechaInspeccion DESC")
    fun getByProducto(productoId: Long): Flow<List<InspeccionCalidad>>
    
    @Query("SELECT * FROM inspecciones_calidad WHERE resultado = :resultado")
    fun getByResultado(resultado: String): Flow<List<InspeccionCalidad>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inspeccion: InspeccionCalidad): Long
    
    @Update
    suspend fun update(inspeccion: InspeccionCalidad)
}
