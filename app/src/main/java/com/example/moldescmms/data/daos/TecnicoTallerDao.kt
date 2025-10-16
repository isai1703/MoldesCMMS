package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.TecnicoTaller
import kotlinx.coroutines.flow.Flow

@Dao
interface TecnicoTallerDao {
    @Query("SELECT * FROM tecnicos_taller WHERE activo = 1 ORDER BY nombre ASC")
    fun getAllTecnicos(): Flow<List<TecnicoTaller>>
    
    @Query("SELECT * FROM tecnicos_taller WHERE id = :id")
    fun getTecnicoById(id: Long): Flow<TecnicoTaller?>
    
    @Query("SELECT * FROM tecnicos_taller WHERE rol = :rol AND activo = 1 ORDER BY nombre ASC")
    fun getTecnicosByRol(rol: String): Flow<List<TecnicoTaller>>
    
    @Query("SELECT * FROM tecnicos_taller WHERE rol = 'Auxiliar' AND activo = 1 ORDER BY solicitudesEnProceso ASC")
    fun getAuxiliaresDisponibles(): Flow<List<TecnicoTaller>>
    
    @Query("SELECT * FROM tecnicos_taller WHERE numeroEmpleado = :numeroEmpleado")
    suspend fun getTecnicoByNumeroEmpleado(numeroEmpleado: String): TecnicoTaller?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tecnico: TecnicoTaller): Long
    
    @Update
    suspend fun update(tecnico: TecnicoTaller)
    
    @Delete
    suspend fun delete(tecnico: TecnicoTaller)
    
    @Query("UPDATE tecnicos_taller SET solicitudesEnProceso = solicitudesEnProceso + 1 WHERE id = :tecnicoId")
    suspend fun incrementarSolicitudesEnProceso(tecnicoId: Long)
    
    @Query("UPDATE tecnicos_taller SET solicitudesEnProceso = solicitudesEnProceso - 1, solicitudesCompletadas = solicitudesCompletadas + 1 WHERE id = :tecnicoId")
    suspend fun completarSolicitud(tecnicoId: Long)
}
