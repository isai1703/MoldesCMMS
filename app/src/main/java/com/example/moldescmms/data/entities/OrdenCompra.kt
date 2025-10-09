package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ordenes_compra")
data class OrdenCompra(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroOrden: String,
    val proveedor: String,
    val fechaOrden: Long = System.currentTimeMillis(),
    val fechaEntregaEstimada: Long? = null,
    val fechaEntregaReal: Long? = null,
    
    val estado: String = "Pendiente", // Pendiente, Aprobada, En Tránsito, Recibida, Cancelada
    
    val subtotal: Double = 0.0,
    val iva: Double = 0.0,
    val total: Double = 0.0,
    
    val moneda: String = "MXN",
    
    val solicitadoPor: String = "",
    val aprobadoPor: String = "",
    val recibidoPor: String = "",
    
    val departamento: String = "",
    val centroCostos: String = "",
    
    val notas: String = "",
    val observaciones: String = "",
    
    val archivoOrden: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
