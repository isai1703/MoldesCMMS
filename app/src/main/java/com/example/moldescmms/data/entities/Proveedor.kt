package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proveedores")
data class Proveedor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val razonSocial: String = "",
    val rfc: String = "",
    
    val contactoPrincipal: String = "",
    val telefono: String = "",
    val email: String = "",
    val sitioWeb: String = "",
    
    val direccion: String = "",
    val ciudad: String = "",
    val estado: String = "",
    val codigoPostal: String = "",
    val pais: String = "México",
    
    // Categorías: Materia Prima, Refacciones, Insumos, Servicios, etc.
    val categoria: String = "",
    val subcategoria: String = "",
    
    val diasCredito: Int = 0,
    val limiteCredito: Double = 0.0,
    val monedaPreferida: String = "MXN",
    
    val calificacion: Double = 0.0, // 0-5 estrellas
    val nivelConfiabilidad: String = "Media", // Alta, Media, Baja
    
    val banco: String = "",
    val cuentaBancaria: String = "",
    val clabe: String = "",
    
    val activo: Boolean = true,
    val notas: String = "",
    
    val fechaRegistro: Long = System.currentTimeMillis(),
    val ultimaCompra: Long? = null,
    val totalCompras: Double = 0.0,
    val numeroCompras: Int = 0,
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
