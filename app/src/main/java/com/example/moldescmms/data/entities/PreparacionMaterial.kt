package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "preparaciones_material",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["materialistaId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = RequerimientoInsumo::class,
            parentColumns = ["id"],
            childColumns = ["requerimientoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["materialistaId"]),
        Index(value = ["productoId"]),
        Index(value = ["requerimientoId"]),
        Index(value = ["estado"]),
        Index(value = ["fechaPreparacion"])
    ]
)
data class PreparacionMaterial(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroPreparacion: String,
    val materialistaId: Long? = null, // Uno de los 3 materialistas
    val requerimientoId: Long? = null,
    val productoId: Long? = null,
    
    // Fecha y turno
    val fechaPreparacion: Long = System.currentTimeMillis(),
    val turno: String = "", // Matutino, Vespertino, Nocturno
    
    // Material preparado
    val materiaPrima: String = "",
    val codigoMaterial: String = "",
    val lote: String = "",
    val cantidadPreparada: Double = 0.0,
    val unidadMedida: String = "KG",
    
    // Aditivos y mezclas
    val pigmento: String = "",
    val cantidadPigmento: Double = 0.0,
    val colorFinal: String = "",
    
    val aditivos: String = "", // Otros aditivos (estabilizantes, antioxidantes, etc.)
    
    // Control de calidad
    val densidad: Double = 0.0,
    val humedad: Double = 0.0,
    val temperatura: Double = 0.0,
    
    val calidadMezcla: String = "Aprobada", // Aprobada, Rechazada, En Revisión
    val observacionesCalidad: String = "",
    
    // Destino
    val maquinaDestino: String = "",
    val moldeDestino: String = "",
    val operadorAsignado: String = "",
    
    // Productividad
    val tiempoPreparacion: Int = 0, // minutos
    val cantidadObjetivo: Double = 0.0,
    val porcentajeEficiencia: Double = 0.0,
    
    // Estado: Preparando, Completada, Entregada, Rechazada
    val estado: String = "Preparando",
    
    val merma: Double = 0.0, // Material desperdiciado
    val motivoMerma: String = "",
    
    val ubicacionAlmacen: String = "",
    
    val notas: String = "",
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
