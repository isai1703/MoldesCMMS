package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val username: String,
    val password: String,
    val email: String = "",
    
    val nombreCompleto: String,
    val numeroEmpleado: String = "",
    val puesto: String = "",
    val departamento: String = "Mantenimiento de Moldes",
    
    val rol: String = "Operador",
    val permisos: String = "",
    
    val telefono: String = "",
    val extension: String = "",
    
    val turno: String = "",
    val area: String = "",
    val supervisor: String = "",
    
    val activo: Boolean = true,
    val verificado: Boolean = false,
    
    val ultimoAcceso: Long? = null,
    
    val fotoUrl: String = "",
    val firmaDigital: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
