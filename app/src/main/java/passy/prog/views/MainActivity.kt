package passy.prog.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import passy.prog.R
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModelPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        viewModel.lista.observe(this){
            val tipo=it
            Log.d("datis",it.toString())
            GlobalScope.launch {

                tipo
            }

        }


        GlobalScope.launch {
            inseriemnto("jjj", "hhh")
        }

    }

    suspend fun inseriemnto(loghin: String = "pipi", password: String = "pop") {
        var titolo: String = "dd".toString()

        viewModel.insertPasswordViewModel(EntityPassword(0, loghin, password))

    }

    suspend fun cancella(entityPassword: EntityPassword){
        viewModel.cancellaTutto(entityPassword)
    }

}