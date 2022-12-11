package passy.prog.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import passy.prog.R
import passy.prog.db.DaoPasswords
import passy.prog.db.DbPAssword
import passy.prog.db.EntityPassword
import passy.prog.views.MainActivity

class Repositorio(context: Context) {
    //instanzia la dao qui nella repo con tutte le sue funzini
    private var daoPasswordsRepositorio: DaoPasswords =
        DbPAssword.getDatabse(context).passwordDaos()
    //liveData collegata alla querry scelta che stampa tutte le tabelle del db
    val stampaTUTToRepo: LiveData<MutableList<EntityPassword>> =
        daoPasswordsRepositorio.getAllPAsswordFromDb()

    fun cancellaPAsswordFromRepo(entityPassword: EntityPassword) {
        daoPasswordsRepositorio.delatePassword(entityPassword)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insetPasswordFromRepo(entityPassword: EntityPassword) {
        daoPasswordsRepositorio.insetPassword(entityPassword)
    }
    //repositorio di FIngerPrint

    fun updatePasswordFromRepo(entityPassword: EntityPassword) {
        daoPasswordsRepositorio.updatePassword(entityPassword)
    }
}