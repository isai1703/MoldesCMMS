package $PKG.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moldes")
data class Molde(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val descripcion: String?,
    val cliente: String?,
    val estado: String = "Disponible"
)
