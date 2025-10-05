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
    indices = [Index(value = ["moldeId"])]
)
data class Mantenimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val moldeId: Long,
    val responsableId: Long? = null,
    
    val tipo: String,
    val subtipo: String = "",
    
    val fechaProgramada: Long,
    val fechaInicio: Long? = null,
    val fechaFinalizacion: Long? = null,
    
    val estado: String = "Pendiente",
    val prioridad: String = "Media",
    
    val descripcion: String = "",
    val trabajosRealizados: String = "",
    val observaciones: String = "",
    
    val refaccionesUsadas: String = "",
    val herramientasUsadas: String = "",
    
    val realizadoPor: String = "",
    val supervisadoPor: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis()
)
