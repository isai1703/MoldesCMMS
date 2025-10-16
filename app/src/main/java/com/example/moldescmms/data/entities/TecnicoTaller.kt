package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tecnicos_taller")
data class TecnicoTaller(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroEmpleado: String,
    val nombre: String,
    val rol: String, // Supervisor, Auxiliar
    
    // Especialidades
    val especialidadPulido: Boolean = false,
    val especialidadSoldadura: Boolean = false,
    val especialidadRectificado: Boolean = false,
    val especialidadAjustes: Boolean = false,
    
    // Turno preferente
    val turnoPreferente: String = "Matutino", // Matutino, Vespertino, Nocturno
    
    // Estadísticas
    val solicitudesCompletadas: Int = 0,
    val solicitudesEnProceso: Int = 0,
    val promedioTiempoCompletado: Double = 0.0, // en horas
    val calificacionPromedio: Double = 0.0, // 0-5
    
    val activo: Boolean = true,
    val telefono: String = "",
    val email: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
