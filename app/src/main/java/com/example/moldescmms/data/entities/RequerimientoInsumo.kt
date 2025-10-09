package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "requerimientos_insumos",
    indices = [Index(value = ["estado"]), Index(value = ["fechaRequerida"])]
)
data class RequerimientoInsumo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroRequerimiento: String = "",
    val productoId: Long? = null,
    
    val tipoInsumo: String,
    val articulo: String,
    val descripcion: String = "",
    val cantidad: Int,
    val unidadMedida: String = "",
    
    val areaSolicitante: String,
    val solicitadoPor: String,
    
    val fechaRequerida: Long,
    val prioridad: String = "Media",
    
    val justificacion: String = "",
    
    val costoEstimado: Double = 0.0,
    val proveedorSugerido: String = "",
    val especificacionesTecnicas: String = "",
    
    val estado: String = "Pendiente",
    val aprobadoPor: String = "",
    val fechaAprobacion: Long? = null,
    val observacionesAprobador: String = "",
    
    val motivoRechazo: String = "",
    
    val fechaCompra: Long? = null,
    val fechaEntrega: Long? = null,
    
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
