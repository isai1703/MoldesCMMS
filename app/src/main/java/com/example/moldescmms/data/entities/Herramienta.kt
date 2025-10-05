package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "herramientas",
    indices = [Index(value = ["codigo"], unique = true)]
)
data class Herramienta(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val marca: String = "",
    val modelo: String = "",
    
    val categoria: String,
    val subcategoria: String = "",
    val descripcion: String = "",
    
    val ubicacion: String = "",
    val responsable: String = "",
    val estadoAsignacion: String = "Disponible",
    
    val estado: String = "Bueno",
    val requiereMantenimiento: Boolean = false,
    
    val cantidad: Int = 1,
    val cantidadMinima: Int = 1,
    val unidadMedida: String = "Pieza",
    
    val proveedor: String = "",
    val costoAdquisicion: Double = 0.0,
    
    val imagenUrl: String = "",
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
