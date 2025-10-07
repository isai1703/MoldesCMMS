package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mantenimientos_maquinas",
    foreignKeys = [
        ForeignKey(
            entity = Maquina::class,
            parentColumns = ["id"],
            childColumns = ["maquinaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["maquinaId"]), Index(value = ["estado"])]
)
data class MantenimientoMaquina(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val maquinaId: Long,
    val tipo: String, // Preventivo, Correctivo, Predictivo
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
    
    val horasParoMaquina: Double = 0.0,
    val costoMantenimiento: Double = 0.0,
    
    val fechaCreacion: Long = System.currentTimeMillis()
)
