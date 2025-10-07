package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ordenes_compra",
    indices = [Index(value = ["numeroOrden"], unique = true), Index(value = ["estado"])]
)
data class OrdenCompra(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroOrden: String,
    val proveedor: String,
    val fechaOrden: Long = System.currentTimeMillis(),
    val fechaEntregaEstimada: Long? = null,
    val fechaEntregaReal: Long? = null,
    
    val estado: String = "Pendiente", // Pendiente, Aprobada, En Tránsito, Recibida, Cancelada
    val prioridad: String = "Media",
    
    // Items (JSON o separado por comas)
    val items: String = "", // codigo:cantidad:precio
    val subtotal: Double = 0.0,
    val impuestos: Double = 0.0,
    val total: Double = 0.0,
    
    val solicitadoPor: String = "",
    val aprobadoPor: String = "",
    val recibidoPor: String = "",
    
    val observaciones: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
