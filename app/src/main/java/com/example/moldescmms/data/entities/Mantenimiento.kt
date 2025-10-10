package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mantenimientos",
    foreignKeys = [
        ForeignKey(
            entity = Molde::class,
            parentColumns = ["id"],
            childColumns = ["moldeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["moldeId"]), Index(value = ["estado"])]
)
data class Mantenimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val moldeId: Long,
    val tipo: String,
    val descripcion: String = "",
    
    val fechaProgramada: Long,
    val fechaRealizada: Long? = null,
    
    val tecnicoAsignado: String = "",
    val estado: String = "Pendiente",
    
    val observaciones: String = "",
    val costoEstimado: Double = 0.0,
    val costoReal: Double = 0.0,
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
