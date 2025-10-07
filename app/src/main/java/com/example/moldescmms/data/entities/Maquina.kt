package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "maquinas",
    foreignKeys = [
        ForeignKey(
            entity = Departamento::class,
            parentColumns = ["id"],
            childColumns = ["departamentoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["codigo"], unique = true), Index(value = ["departamentoId"])]
)
data class Maquina(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val marca: String = "",
    val modelo: String = "",
    val numeroSerie: String = "",
    
    val departamentoId: Long? = null,
    val tipo: String = "", // Inyectora, Sopladora, etc.
    val tonelaje: Int = 0,
    val capacidad: String = "",
    
    val estado: String = "Operativa", // Operativa, En Mantenimiento, Fuera de Servicio
    val ubicacion: String = "",
    
    // Contadores
    val horasOperacion: Double = 0.0,
    val ciclosTotales: Long = 0,
    val ultimoMantenimiento: Long? = null,
    val proximoMantenimiento: Long? = null,
    
    val observaciones: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
