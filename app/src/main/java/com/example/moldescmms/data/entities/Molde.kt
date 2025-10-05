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
    val ubicacion: String = "",
    
    val numeroCavidades: Int = 1,
    val peso: Double = 0.0,
    val dimensiones: String = "",
    
    val tipoColada: String = "",
    val numeroCanalesColada: Int = 0,
    
    val tipoExpulsor: String = "",
    val numeroExpulsores: Int = 0,
    
    val numeroCircuitosRefrigerante: Int = 0,
    val numeroConexionesRapidas: Int = 0,
    val tipoConexionRefrigerante: String = "",
    val descripcionCircuitoRefrigerante: String = "",
    
    val numeroCircuitosAire: Int = 0,
    val numeroConexionesAire: Int = 0,
    val tipoConexionAire: String = "",
    val descripcionCircuitoAire: String = "",
    
    val tieneMuelasGeneradorasRosca: Boolean = false,
    val numeroMuelasRosca: Int = 0,
    val tipoRosca: String = "",
    
    val tieneInsertos: Boolean = false,
    val numeroInsertos: Int = 0,
    val tipoInsertos: String = "",
    
    val tieneCorrederas: Boolean = false,
    val numeroCorrederas: Int = 0,
    
    val tieneLevantadores: Boolean = false,
    val numeroLevantadores: Int = 0,
    
    val estado: String = "Activo",
    val prioridadMantenimiento: String = "Media",
    val frecuenciaMantenimientoPreventivo: Int = 30,
    val ultimoMantenimiento: Long? = null,
    val proximoMantenimiento: Long? = null,
    
    val fabricante: String = "",
    val anoFabricacion: Int? = null,
    val numeroSerie: String = "",
    val materialesProcesa: String = "",
    
    val observaciones: String = "",
    val imagenUrl: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis(),
    val creadoPor: Long? = null,
    val actualizadoPor: Long? = null
)
