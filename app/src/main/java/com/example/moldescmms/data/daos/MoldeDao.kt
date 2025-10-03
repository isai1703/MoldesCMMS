package $PKG.data.daos

import androidx.room.*
import $PKG.data.entities.Molde

@Dao
interface MoldeDao {
    @Query("SELECT * FROM moldes")
    fun getAll(): List<Molde>

    @Insert
    fun insert(m: Molde): Long

    @Update
    fun update(m: Molde)

    @Delete
    fun delete(m: Molde)
}
