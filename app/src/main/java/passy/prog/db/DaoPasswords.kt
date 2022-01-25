package passy.prog.db

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface DaoPasswords {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetPassword(entityPassword: EntityPassword)

    @Delete
    fun delatePassword(entityPassword: EntityPassword)

    @Update
    fun updatePassword(entityPassword: EntityPassword)

    @Query("select * from passList order by loghin asc")
    fun getAllPAsswordFromDb(): LiveData<List<EntityPassword>>

}