package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "asignaciones_produccion",
    foreignKeys = [
        ForeignKey(
            entity = Operador::class,
            parentColumns = ["id"],
            childColumns = ["operadorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Maquina::class,
            parentColumns = ["id"],
            childColumns = ["maquinaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Molde::class,
            parentColumns = ["id"],
            childColumns = ["moldeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["operadorId"]),
        Index(value = ["maquinaId"]),
        Index(value = ["moldeId"]),
        Index(value = ["productoId"]),
        Index(value = ["fechaAsignacion"]),
        Index(value = ["estado"])
    ]
)
data class AsignacionProduccion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroAsignacion: String,
    
    // Asignación
    val operadorId: Long,
    val maquinaId: Long,
    val moldeId: Long,
    val productoId: Long? = null,
    
    // Fechas
    val fechaAsignacion: Long = System.currentTimeMillis(),
    val fechaInicio: Long? = null,
    val fechaFin: Long? = null,
    
    val turno: String = "", // Matutino, Vespertino, Nocturno
    val supervisor: String = "",
    
    // Objetivos de producción
    val cantidadObjetivo: Int = 0,
    val cantidadProducida: Int = 0,
    val cantidadDefectuosa: Int = 0,
    val cantidadAprobada: Int = 0,
    
    // Tiempos
    val tiempoSetup: Int = 0, // minutos
    val tiempoProduccion: Int = 0, // minutos
    val tiempoParos: Int = 0, // minutos
    val motivosParos: String = "",
    
    // Eficiencia
    val ciclosReales: Int = 0,
    val ciclosEsperados: Int = 0,
    val porcentajeEficiencia: Double = 0.0,
    val porcentajeCalidad: Double = 0.0,
    
    // Estado: Programada, En Proceso, Completada, Pausada, Cancelada
    val estado: String = "Programada",
    
    // Incidencias
    val incidencias: String = "",
    val tiempoMuerto: Int = 0,
    val razonTiempoMuerto: String = "",
    
    // Material
    val materialUtilizado: Double = 0.0, // KG
    val materialDesperdiciado: Double = 0.0, // KG
    
    val notas: String = "",
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
