package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departamentos")
data class Departamento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val tipo: String, // Producción, Almacén, Calidad, Compras
    val responsable: String = "",
    val activo: Boolean = true
)
