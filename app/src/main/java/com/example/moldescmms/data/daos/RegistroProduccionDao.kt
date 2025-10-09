package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.RegistroProduccion
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroProduccionDao {
    @Query("SELECT * FROM registros_produccion ORDER BY fechaInicio DESC")
    fun getAll(): Flow<List<RegistroProduccion>>
    
    @Query("SELECT * FROM registros_produccion WHERE id = :id")
    suspend fun getById(id: Long): RegistroProduccion?
    
    @Query("SELECT * FROM registros_produccion WHERE operadorId = :operadorId ORDER BY fechaInicio DESC")
    fun getByOperador(operadorId: Long): Flow<List<RegistroProduccion>>
    
    @Query("SELECT * FROM registros_produccion WHERE maquinaId = :maquinaId ORDER BY fechaInicio DESC")
    fun getByMaquina(maquinaId: Long): Flow<List<RegistroProduccion>>
    
    @Query("SELECT * FROM registros_produccion WHERE fechaInicio BETWEEN :inicio AND :fin")
    fun getByRangoFecha(inicio: Long, fin: Long): Flow<List<RegistroProduccion>>
    
    @Query("SELECT * FROM registros_produccion WHERE turno = :turno ORDER BY fechaInicio DESC")
    fun getByTurno(turno: String): Flow<List<RegistroProduccion>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registro: RegistroProduccion): Long
    
    @Update
    suspend fun update(registro: RegistroProduccion)
}
