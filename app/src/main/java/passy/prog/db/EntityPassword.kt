package passy.prog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passList")
data class EntityPassword(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "descrizione") val descrizione: String?=null,
    @ColumnInfo(name = "loghin") val loghin: String? = null,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "color") val color: String?=null,
    @ColumnInfo(name = "data") val data: String?=null

    )
