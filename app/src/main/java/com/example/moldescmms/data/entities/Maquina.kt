package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maquinas")
data class Maquina(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val modelo: String,
    val numeroSerie: String = "",
    val fabricante: String = "",
    
    val tonelaje: Int = 0,
    val nave: String = "Nave 1",
    
    val ubicacion: String = "",
    val estado: String = "Operativa",
    
    val fechaAdquisicion: Long? = null,
    val fechaUltimoMantenimiento: Long? = null,
    val fechaProximoMantenimiento: Long? = null,
    
    val horasUso: Int = 0,
    
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
