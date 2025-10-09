package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operadores")
data class Operador(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val numeroEmpleado: String,
    val nombreCompleto: String,
    val departamento: String,
    val turno: String,
    
    val telefono: String = "",
    val email: String = "",
    
    val nivelExperiencia: String = "Operador Jr",
    val certificaciones: String = "",
    
    val activo: Boolean = true,
    
    val fechaIngreso: Long = System.currentTimeMillis(),
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaActualizacion: Long = System.currentTimeMillis()
)
