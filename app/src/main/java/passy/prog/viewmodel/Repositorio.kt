package passy.prog.viewmodel

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import passy.prog.db.DaoPasswords
import passy.prog.db.DbPAssword
import passy.prog.db.EntityPassword

class Repositorio(context: Context) {

    //instanzia la dao qui nella repo con tutte le sue funzini
    private var daoPasswordsRepositorio: DaoPasswords =
        DbPAssword.getDatabse(context).passwordDaos()

    //liveData collegata alla querry scelta che stampa tutte le tabelle del db
    val stampaTUTToRepo: LiveData<List<EntityPassword>> =
        daoPasswordsRepositorio.getAllPAsswordFromDb()


    fun cancellaPAsswordFromRepo(entityPassword: EntityPassword) {
        daoPasswordsRepositorio.delatePassword(entityPassword)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insetPasswordFromRepo(entityPassword: EntityPassword){
        daoPasswordsRepositorio.insetPassword(entityPassword)
    }

}