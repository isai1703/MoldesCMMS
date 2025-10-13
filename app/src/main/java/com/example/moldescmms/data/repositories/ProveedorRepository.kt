package com.example.moldescmms.data.repositories

import com.example.moldescmms.data.daos.ProveedorDao
import com.example.moldescmms.data.entities.Proveedor
import kotlinx.coroutines.flow.Flow

class ProveedorRepository(private val proveedorDao: ProveedorDao) {
    
    val allProveedores: Flow<List<Proveedor>> = proveedorDao.getAllProveedores()
    
    fun getProveedorById(id: Long): Flow<Proveedor?> = proveedorDao.getProveedorById(id)
    
    suspend fun getProveedorByCodigo(codigo: String): Proveedor? = proveedorDao.getProveedorByCodigo(codigo)
    
    fun getProveedoresByCategoria(categoria: String): Flow<List<Proveedor>> = 
        proveedorDao.getProveedoresByCategoria(categoria)
    
    fun buscarProveedores(busqueda: String): Flow<List<Proveedor>> = 
        proveedorDao.buscarProveedores(busqueda)
    
    suspend fun insert(proveedor: Proveedor): Long = proveedorDao.insert(proveedor)
    
    suspend fun update(proveedor: Proveedor) = proveedorDao.update(proveedor)
    
    suspend fun delete(proveedor: Proveedor) = proveedorDao.delete(proveedor)
    
    suspend fun desactivar(id: Long) = proveedorDao.desactivar(id)
}
