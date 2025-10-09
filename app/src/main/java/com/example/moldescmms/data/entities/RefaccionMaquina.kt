package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refacciones_maquinas")
data class RefaccionMaquina(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val codigo: String,
    val codigoRefaccion: String,
    val nombre: String,
    val descripcion: String = "",
    val numeroParte: String,
    
    val categoria: String,
    val modeloMaquina: String,
    val modelosCompatibles: String = "",
    
    val stockActual: Int = 0,
    val stockMinimo: Int = 0,
    val stockMaximo: Int = 0,
    val unidadMedida: String = "Pieza",
    
    val ubicacionAlmacen: String = "",
    
    val precioUnitario: Double = 0.0,
    val proveedor: String = "",
    val tiempoEntregaDias: Int = 0,
    
    val especificaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
