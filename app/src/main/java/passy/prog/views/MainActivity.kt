package passy.prog.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

        this.hideToolbarAndStatusBar(this)
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]

    }
    suspend fun cancella(entityPassword: EntityPassword) {
        viewModel.cancellaTutto(entityPassword)
    }

}