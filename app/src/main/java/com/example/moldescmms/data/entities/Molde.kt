package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moldes")
data class Molde(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val descripcion: String = "",
    
    val numeroSerie: String = "",
    val fabricante: String = "",
    
    val numeroCavidades: Int = 1,
    val material: String = "",
    
    val ubicacion: String = "",
    val estado: String = "Disponible",
    
    val tonelajeRequerido: Int = 0,
    val cicloProduccion: Int = 0,
    
    val fechaAdquisicion: Long? = null,
    val fechaUltimoMantenimiento: Long? = null,
    val fechaProximoMantenimiento: Long? = null,
    
    val horasUso: Int = 0,
    val ciclosTotales: Int = 0,
    
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
