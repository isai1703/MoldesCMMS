package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.ItemOrdenCompra
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemOrdenCompraDao {
    @Query("SELECT * FROM items_orden_compra WHERE ordenCompraId = :ordenCompraId")
    fun getByOrdenCompra(ordenCompraId: Long): Flow<List<ItemOrdenCompra>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemOrdenCompra): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemOrdenCompra>)
    
    @Update
    suspend fun update(item: ItemOrdenCompra)
    
    @Delete
    suspend fun delete(item: ItemOrdenCompra)
    
    @Query("DELETE FROM items_orden_compra WHERE ordenCompraId = :ordenCompraId")
    suspend fun deleteByOrdenCompra(ordenCompraId: Long)
}
