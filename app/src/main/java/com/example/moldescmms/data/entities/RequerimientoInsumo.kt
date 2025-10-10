package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "requerimientos_insumos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["supervisorId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["supervisorId"]),
        Index(value = ["productoId"]),
        Index(value = ["estado"]),
        Index(value = ["fechaRequerida"])
    ]
)
data class RequerimientoInsumo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroRequerimiento: String = "",
    val supervisorId: Long? = null, // ID del supervisor que solicita
    val productoId: Long? = null,
    
    // Tipo de insumo
    val tipoInsumo: String, // Materia Prima, Pigmento, Tapas, Asas Plásticas, Asas Metal, Aditivos, Otros
    val articulo: String,
    val descripcion: String = "",
    val cantidad: Int,
    val unidadMedida: String = "",
    
    val areaSolicitante: String, // Producción, Material Estas, etc.
    val solicitadoPor: String,
    
    val fechaRequerida: Long,
    val prioridad: String = "Media", // Urgente, Alta, Media, Baja
    
    val justificacion: String = "",
    
    val costoEstimado: Double = 0.0,
    val proveedorSugerido: String = "",
    val especificacionesTecnicas: String = "",
    
    // Estados: Pendiente, Aprobado, Preparado, Entregado, Rechazado
    val estado: String = "Pendiente",
    val aprobadoPor: String = "",
    val fechaAprobacion: Long? = null,
    val observacionesAprobador: String = "",
    
    val motivoRechazo: String = "",
    
    val preparadoPor: String = "", // Personal de almacén que prepara
    val fechaPreparacion: Long? = null,
    val entregadoA: String = "",
    val fechaEntrega: Long? = null,
    
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
