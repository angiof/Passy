package passy.prog.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import passy.prog.db.EntityPassword

class ViewModelPassword(application: Application) : AndroidViewModel(application) {

    private val repositorioFromViewModel: Repositorio = Repositorio(application)


    fun insertPasswordViewModel(entityPassword: EntityPassword) =
        viewModelScope.launch {
            repositorioFromViewModel.insetPasswordFromRepo(entityPassword)
        }

    val lista: LiveData<List<EntityPassword>> = repositorioFromViewModel.stampaTUTToRepo


    suspend fun cancellaTutto(entityPassword: EntityPassword) {
        repositorioFromViewModel.cancellaPAsswordFromRepo(entityPassword = entityPassword)
    }


}