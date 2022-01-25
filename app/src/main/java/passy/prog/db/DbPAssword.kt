package passy.prog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityPassword::class], version = 1, exportSchema = false)
abstract class DbPAssword : RoomDatabase() {

    abstract fun passwordDaos(): DaoPasswords

    //singleton intanzia varie instanzie di esso nello stesso tempo
    companion object {
        //instanzia questo db e informa ai altri trad che questa intanzia esiste
        @Volatile
        private var INSTANCE: DbPAssword? = null

        fun getDatabse(context: Context): DbPAssword {
            //se la instazia non è null crea il db
            //altrimenti carica il suo contenuto
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbPAssword::class.java,
                    "dbPassword"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}