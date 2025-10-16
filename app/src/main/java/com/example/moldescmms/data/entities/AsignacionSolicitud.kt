package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "asignaciones_solicitud",
    foreignKeys = [
        ForeignKey(
            entity = SolicitudMantenimiento::class,
            parentColumns = ["id"],
            childColumns = ["solicitudId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TecnicoTaller::class,
            parentColumns = ["id"],
            childColumns = ["tecnicoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["solicitudId"]),
        Index(value = ["tecnicoId"]),
        Index(value = ["estado"]),
        Index(value = ["fechaAsignacion"])
    ]
)
data class AsignacionSolicitud(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val solicitudId: Long,
    val tecnicoId: Long,
    val tecnicoNombre: String, // Desnormalizado para consultas rápidas
    
    val asignadoPor: String, // Nombre del supervisor
    val supervisorId: Long? = null,
    
    val fechaAsignacion: Long = System.currentTimeMillis(),
    val fechaInicioReal: Long? = null,
    val fechaFinalizacion: Long? = null,
    
    val turnoAsignado: String, // Matutino, Vespertino, Nocturno
    
    // Estado: Asignada, En Proceso, Pausada, Completada, Cancelada
    val estado: String = "Asignada",
    
    val prioridad: String = "Media", // Heredada de la solicitud
    
    // Notas del supervisor al asignar
    val notasAsignacion: String = "",
    
    // Notas del técnico al trabajar
    val notasTecnico: String = "",
    
    // Progreso
    val porcentajeAvance: Int = 0,
    
    // Tiempos
    val tiempoEstimado: Int = 0, // minutos
    val tiempoReal: Int = 0, // minutos
    
    // Evaluación
    val calificacionSupervisor: Int? = null, // 1-5
    val comentariosSupervisor: String = "",
    
    // Motivo de reasignación o cancelación
    val motivoCambioEstado: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
