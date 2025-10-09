package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "solicitudes_mantenimiento",
    foreignKeys = [
        ForeignKey(
            entity = Molde::class,
            parentColumns = ["id"],
            childColumns = ["moldeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["moldeId"]),
        Index(value = ["estado"]),
        Index(value = ["fechaSolicitud"])
    ]
)
data class SolicitudMantenimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val moldeId: Long,
    val solicitanteId: Long? = null,
    
    // Origen ampliado - ahora incluye Mant. Máquinas
    val departamentoOrigen: String, // Producción, Calidad, Mantenimiento Máquinas
    val areaSolicitante: String = "",
    val turno: String = "",
    
    val tipo: String,
    val prioridad: String = "Media",
    
    val subtipo: String, // Pulido, Limpieza, Reparación, Cambio Conexión, Cambio Manguera
    
    val detalleConexion: String = "",
    val tipoConexionY: Boolean = false,
    val calibreManguera: String = "", // 8, 10, 12
    val cantidadMangueras: Int = 0,
    val longitudManguera: String = "",
    
    val problemaReportado: String,
    val condicionMolde: String = "",
    val fotoProblema: String = "",
    
    val afectaProduccion: Boolean = false,
    val piezasDefectuosas: Int = 0,
    val tiempoParoEstimado: String = "",
    
    val fechaSolicitud: Long = System.currentTimeMillis(),
    val fechaRequerida: Long? = null,
    
    val estado: String = "Pendiente",
    val motivoRechazo: String = "",
    
    val tecnicoAsignado: Long? = null,
    val fechaAsignacion: Long? = null,
    val fechaInicio: Long? = null,
    val fechaCompletado: Long? = null,
    
    val mantenimientoId: Long? = null,
    
    val comentariosSolicitante: String = "",
    val comentariosTaller: String = "",
    val observacionesCierre: String = "",
    
    val requiereAprobacion: Boolean = true,
    val aprobadoPor: String = "",
    val fechaAprobacion: Long? = null,
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
