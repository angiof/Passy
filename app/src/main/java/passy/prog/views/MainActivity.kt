package passy.prog.views

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import passy.prog.R
import passy.prog.utils.*

open class MainActivity : AppCompatActivity() {

    val utilsBiometrick by lazy {
        UtilsFuns.PassyCheckersBiometrick(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forceToDayMode()
        blockScreenShots(this)
        setContentView(R.layout.activity_main)
        checkRootAndCloseApp(this)
        face()
    }

    override fun onResume() {
        super.onResume()
        face()
    }

    override fun getOnBackInvokedDispatcher():
        OnBackInvokedDispatcher {
        finishAffinity()
        finish()
        return super.getOnBackInvokedDispatcher()

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
