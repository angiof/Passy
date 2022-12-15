package passy.prog.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passList")
data class EntityPassword(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "descrizione") val descrizione: String? = null,
    @ColumnInfo(name = "loghin") val loghin: String? = null,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "color") val color: String? = null,
    @ColumnInfo(name = "data") val data: String? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(descrizione)
        parcel.writeString(loghin)
        parcel.writeString(password)
        parcel.writeString(color)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntityPassword> {
        override fun createFromParcel(parcel: Parcel): EntityPassword {
            return EntityPassword(parcel)
        }

        override fun newArray(size: Int): Array<EntityPassword?> {
            return arrayOfNulls(size)
        }
    }
}
