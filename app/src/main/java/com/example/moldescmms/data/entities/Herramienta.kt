package $PKG.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "herramientas")
data class Herramienta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val cantidad: Int,
    val ubicacion: String?
)
