package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "registros_produccion",
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
        Index(value = ["fechaInicio"])
    ]
)
data class RegistroProduccion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val operadorId: Long,
    val maquinaId: Long,
    val moldeId: Long,
    val productoId: Long? = null,
    
    val turno: String,
    
    val fechaInicio: Long,
    val fechaFin: Long? = null,
    
    val piezasProducidas: Int = 0,
    val piezasDefectuosas: Int = 0,
    
    val tiempoParoMinutos: Int = 0,
    val motivoParo: String = "",
    
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
