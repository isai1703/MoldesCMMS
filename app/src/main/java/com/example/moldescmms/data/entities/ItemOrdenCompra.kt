package com.example.moldescmms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items_orden_compra",
    foreignKeys = [
        ForeignKey(
            entity = OrdenCompra::class,
            parentColumns = ["id"],
            childColumns = ["ordenCompraId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["ordenCompraId"])]
)
data class ItemOrdenCompra(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val ordenCompraId: Long,
    
    val articulo: String,
    val descripcion: String = "",
    val cantidad: Int,
    val unidadMedida: String,
    val precioUnitario: Double,
    val subtotal: Double,
    
    val especificaciones: String = "",
    
    val fechaCreacion: Long = System.currentTimeMillis()
)
