package passy.prog.viewmodel

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import passy.prog.db.DaoPasswords
import passy.prog.db.DbPAssword
import passy.prog.db.EntityPassword
import javax.inject.Inject

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
    suspend fun insetPasswordFromRepo(entityPassword: EntityPassword){
        daoPasswordsRepositorio.insetPassword(entityPassword)
    }
    //repositorio di FIngerPrint






}