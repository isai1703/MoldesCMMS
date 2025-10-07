package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.OrdenCompra
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdenCompraDao {
    @Query("SELECT * FROM ordenes_compra ORDER BY fechaOrden DESC")
    fun getAll(): Flow<List<OrdenCompra>>
    
    @Query("SELECT * FROM ordenes_compra WHERE id = :id")
    suspend fun getById(id: Long): OrdenCompra?
    
    @Query("SELECT * FROM ordenes_compra WHERE estado = :estado")
    fun getByEstado(estado: String): Flow<List<OrdenCompra>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orden: OrdenCompra): Long
    
    @Update
    suspend fun update(orden: OrdenCompra)
}
