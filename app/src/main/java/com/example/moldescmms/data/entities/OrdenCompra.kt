package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx:room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ordenes_compra",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["solicitanteId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["solicitanteId"]),
        Index(value = ["estado"]),
        Index(value = ["nivelUrgencia"]),
        Index(value = ["fechaOrden"])
    ]
)
data class OrdenCompra(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroOrden: String,
    val solicitanteId: Long? = null,
    val departamentoSolicitante: String,
    
    val proveedor: String,
    val contactoProveedor: String = "",
    val telefonoProveedor: String = "",
    val emailProveedor: String = "",
    
    val fechaOrden: Long = System.currentTimeMillis(),
    val fechaEntregaEstimada: Long? = null,
    val fechaEntregaReal: Long? = null,
    
    // Niveles de urgencia: Urgente, Alta, Normal, Baja
    val nivelUrgencia: String = "Normal",
    val justificacionUrgencia: String = "",
    
    // Estados: Pendiente, Aprobada, En Proceso, Completada, Cancelada
    val estado: String = "Pendiente",
    
    val subtotal: Double = 0.0,
    val iva: Double = 0.0,
    val total: Double = 0.0,
    
    val moneda: String = "MXN",
    
    val aprobadoPor: String = "",
    val fechaAprobacion: Long? = null,
    
    val recibidoPor: String = "",
    val fechaRecepcion: Long? = null,
    
    val centroCostos: String = "",
    
    val notas: String = "",
    val observaciones: String = "",
    
    val archivoOrden: String = "", // Path del archivo adjunto
    val archivoFactura: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
