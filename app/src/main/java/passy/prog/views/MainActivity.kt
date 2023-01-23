package passy.prog.views

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import passy.prog.R
import passy.prog.utils.UtilsFuns
import passy.prog.utils.blockScreenShots
import passy.prog.utils.checkRootAndCloseApp
import passy.prog.utils.face

open class MainActivity : AppCompatActivity() {

    val utilsBiometrick by lazy {
        UtilsFuns.PassyCheckersBiometrick(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
