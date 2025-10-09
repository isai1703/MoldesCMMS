package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "refacciones_maquinas",
    indices = [Index(value = ["codigo"], unique = true), Index(value = ["modeloMaquina"])]
)
data class RefaccionMaquina(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val nombre: String,
    val descripcion: String = "",
    
    // Para qué modelos de máquina es compatible
    val modeloMaquina: String = "", // Modelo específico o "Universal"
    val modelosCompatibles: String = "", // Lista separada por comas
    
    val categoria: String, // Motor, Resistencias, Sensores, Hidráulico, etc.
    val marca: String = "",
    val numerosParte: String = "",
    
    val stockActual: Int = 0,
    val stockMinimo: Int = 5,
    val stockMaximo: Int = 100,
    val ubicacionAlmacen: String = "",
    
    val proveedorPrincipal: String = "",
    val costoUnitario: Double = 0.0,
    val tiempoEntrega: Int = 0, // días
    
    val observaciones: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
