package passy.prog.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import passy.prog.db.EntityPassword

open class ViewModelPassword(application: Application) : AndroidViewModel(application) {

    private val repositorioFromViewModel: Repositorio = Repositorio(application)


    fun insertPasswordViewModel(entityPassword: EntityPassword) =
        viewModelScope.launch {
            repositorioFromViewModel.insetPasswordFromRepo(entityPassword)
        }
    val lista: LiveData<MutableList<EntityPassword>> = repositorioFromViewModel.stampaTUTToRepo

    fun hidebtnFRomRepo(view: View) {
        repositorioFromViewModel.hidebtn(view)
    }

    fun cancellaTutto(entityPassword: EntityPassword) {
        repositorioFromViewModel.cancellaPAsswordFromRepo(entityPassword)
    }

    fun btnhide2(context: Context) {
        repositorioFromViewModel.viess(context)
    }

    fun mostraToas(activity: Activity,messaggio: String){
        repositorioFromViewModel.mostraToas(activity ,messaggio)
    }
    fun updatePassword(entityPassword: EntityPassword){
        repositorioFromViewModel.updatePasswordFromRepo(entityPassword)
    }
}




