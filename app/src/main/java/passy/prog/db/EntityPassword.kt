package passy.prog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passList")
data class EntityPassword(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "loghin") val loghin: String?,
    @ColumnInfo(name = "password") val password: String?

)
