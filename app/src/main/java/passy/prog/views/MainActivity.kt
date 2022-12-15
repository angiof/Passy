package passy.prog.views

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import passy.prog.R
import passy.prog.utils.UtilsFuns
import passy.prog.utils.face

open class MainActivity : AppCompatActivity() {

    val utilsBiometrick by lazy {
        UtilsFuns.PassyCheckersBiometrick(this@MainActivity)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //face()

    }

    override fun onResume() {
        //  idFInger()
        super.onResume()
        //face()
    }


    override fun onBackPressed() {
        finishAffinity()
        finish()
        super.onBackPressed()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}
