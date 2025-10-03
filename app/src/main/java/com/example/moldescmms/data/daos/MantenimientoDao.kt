package $PKG.data.daos

import androidx.room.*
import $PKG.data.entities.Mantenimiento

@Dao
interface MantenimientoDao {
    @Query("SELECT * FROM mantenimientos WHERE moldeId = :moldeId")
    fun forMolde(moldeId: Long): List<Mantenimiento>

    @Insert
    fun insert(m: Mantenimiento): Long
}
