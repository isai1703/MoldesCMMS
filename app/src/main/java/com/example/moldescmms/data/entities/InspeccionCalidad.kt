package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inspecciones_calidad",
    foreignKeys = [
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["productoId"]), Index(value = ["fechaInspeccion"])]
)
data class InspeccionCalidad(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val productoId: Long,
    val loteNumero: String = "",
    val fechaInspeccion: Long = System.currentTimeMillis(),
    
    val cantidadInspeccionada: Int = 0,
    val cantidadAprobada: Int = 0,
    val cantidadRechazada: Int = 0,
    
    val resultado: String = "Aprobado", // Aprobado, Rechazado, Condicional
    val inspector: String = "",
    
    // Mediciones
    val mediciones: String = "", // JSON con mediciones específicas
    val defectosEncontrados: String = "",
    val causaRechazo: String = "",
    
    val accionesCorrectivas: String = "",
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis()
)
