package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "refacciones",
    indices = [Index(value = ["codigo"], unique = true)]
)
data class Refaccion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val descripcion: String = "",
    val marca: String = "",
    
    val categoria: String,
    val subcategoria: String = "",
    val tipoMaterial: String = "",
    
    val dimensiones: String = "",
    val peso: Double = 0.0,
    
    val tipoConexion: String = "",
    val tipoRosca: String = "",
    
    val stockActual: Int = 0,
    val stockMinimo: Int = 5,
    val stockMaximo: Int = 100,
    val unidadMedida: String = "Pieza",
    val ubicacionAlmacen: String = "",
    
    val estadoInventario: String = "Normal",
    val requiereReorden: Boolean = false,
    
    val proveedorPrincipal: String = "",
    val costoUnitario: Double = 0.0,
    val moneda: String = "MXN",
    
    val moldesCompatibles: String = "",
    val aplicaciones: String = "",
    
    val imagenUrl: String = "",
    val observaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
