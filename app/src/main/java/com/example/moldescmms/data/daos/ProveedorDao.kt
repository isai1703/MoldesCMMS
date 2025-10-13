package com.example.moldescmms.data.daos

import androidx.room.*
import com.example.moldescmms.data.entities.Proveedor
import kotlinx.coroutines.flow.Flow

@Dao
interface ProveedorDao {
    @Query("SELECT * FROM proveedores WHERE activo = 1 ORDER BY nombre ASC")
    fun getAllProveedores(): Flow<List<Proveedor>>
    
    @Query("SELECT * FROM proveedores WHERE id = :id")
    fun getProveedorById(id: Long): Flow<Proveedor?>
    
    @Query("SELECT * FROM proveedores WHERE codigo = :codigo")
    suspend fun getProveedorByCodigo(codigo: String): Proveedor?
    
    @Query("SELECT * FROM proveedores WHERE categoria = :categoria AND activo = 1 ORDER BY nombre ASC")
    fun getProveedoresByCategoria(categoria: String): Flow<List<Proveedor>>
    
    @Query("SELECT * FROM proveedores WHERE nombre LIKE '%' || :busqueda || '%' OR razonSocial LIKE '%' || :busqueda || '%'")
    fun buscarProveedores(busqueda: String): Flow<List<Proveedor>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(proveedor: Proveedor): Long
    
    @Update
    suspend fun update(proveedor: Proveedor)
    
    @Delete
    suspend fun delete(proveedor: Proveedor)
    
    @Query("UPDATE proveedores SET activo = 0 WHERE id = :id")
    suspend fun desactivar(id: Long)
}
