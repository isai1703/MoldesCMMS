package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx:room.PrimaryKey

@Entity(
    tableName = "ordenes_compra",
    indices = [Index(value = ["numeroOrden"], unique = true), Index(value = ["estado"])]
)
data class OrdenCompra(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroOrden: String,
    val proveedor: String,
    val departamentoSolicitante: String, // Qué departamento solicita
    
    // Clasificación de urgencia
    val nivelUrgencia: String = "Normal", // Crítica, Urgente, Normal, Programada
    val justificacionUrgencia: String = "", // Obligatorio si es Crítica o Urgente
    val afectaProduccion: Boolean = false,
    
    val fechaOrden: Long = System.currentTimeMillis(),
    val fechaEntregaEstimada: Long? = null,
    val fechaEntregaReal: Long? = null,
    
    val estado: String = "Pendiente", // Pendiente, Aprobada, En Proceso, Completada, Cancelada
    val prioridad: String = "Media",
    
    val items: String = "", // JSON: [{codigo, descripcion, cantidad, precio}]
    val subtotal: Double = 0.0,
    val impuestos: Double = 0.0,
    val total: Double = 0.0,
    
    val solicitadoPor: String = "",
    val aprobadoPor: String = "",
    val recibidoPor: String = "",
    
    // Archivo/Historial
    val archivoCompra: String = "", // PDF, imágenes de facturas
    val numeroFactura: String = "",
    val fechaArchivado: Long? = null,
    
    val observaciones: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
