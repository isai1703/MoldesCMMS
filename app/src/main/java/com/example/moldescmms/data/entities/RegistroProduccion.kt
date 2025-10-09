package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "registros_produccion",
    foreignKeys = [
        ForeignKey(entity = Operador::class, parentColumns = ["id"], 
                   childColumns = ["operadorId"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = Maquina::class, parentColumns = ["id"],
                   childColumns = ["maquinaId"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = Molde::class, parentColumns = ["id"],
                   childColumns = ["moldeId"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = Producto::class, parentColumns = ["id"],
                   childColumns = ["productoId"], onDelete = ForeignKey.SET_NULL)
    ],
    indices = [
        Index(value = ["operadorId"]),
        Index(value = ["maquinaId"]),
        Index(value = ["fechaInicio"])
    ]
)
data class RegistroProduccion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val operadorId: Long,
    val maquinaId: Long,
    val moldeId: Long,
    val productoId: Long,
    
    val turno: String,
    val fechaInicio: Long,
    val fechaFin: Long? = null,
    
    val piezasProducidas: Int = 0,
    val piezasDefectuosas: Int = 0,
    val piezasBuenas: Int = 0,
    
    val tiempoCiclo: Double = 0.0, // segundos
    val horasOperacion: Double = 0.0,
    val parosRegistrados: String = "", // JSON con paros
    
    val observaciones: String = "",
    val fechaCreacion: Long = System.currentTimeMillis()
)
