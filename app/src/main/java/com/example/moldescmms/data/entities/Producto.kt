package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "productos",
    indices = [Index(value = ["codigo"], unique = true)]
)
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val descripcion: String = "",
    val tipo: String = "", // Terminado, Semi-terminado, Materia Prima
    
    // Inventario
    val stockActual: Int = 0,
    val stockMinimo: Int = 0,
    val stockMaximo: Int = 100,
    val unidadMedida: String = "Pieza",
    val ubicacionAlmacen: String = "",
    
    // Producción
    val moldeAsociado: Long? = null,
    val tiempoCiclo: Double = 0.0, // segundos
    val pesoPieza: Double = 0.0, // gramos
    
    // Calidad
    val especificaciones: String = "",
    val tolerancias: String = "",
    
    // Costos
    val costoProduccion: Double = 0.0,
    val precioVenta: Double = 0.0,
    
    val activo: Boolean = true,
    val fechaCreacion: Long = System.currentTimeMillis()
)
