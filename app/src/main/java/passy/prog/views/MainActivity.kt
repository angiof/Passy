package passy.prog.views

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.core.content.ContextCompat
import passy.prog.R
import passy.prog.utils.UtilsFuns
import java.util.concurrent.Executor

open class MainActivity : AppCompatActivity() {

    private val utilsFuns by lazy {
        UtilsFuns().PassySettings(this@MainActivity).settingThemeMode()
    }

    companion object {
        val ecco: Companion get() = MainActivity
    }

    private val utilsBiometrick by lazy { UtilsFuns.PassyCheckersBiometrick(this@MainActivity) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        utilsFuns
        face()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        //  idFInger()
        face()
        super.onResume()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBackPressed() {
        finishAffinity()
        finish()
        super.onBackPressed()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun face() {
        val executor: Executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt: androidx.biometric.BiometricPrompt =
            androidx.biometric.BiometricPrompt(
                this,
                executor,
                object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                    }

                    override fun onAuthenticationFailed() {
                        finishAffinity()
                        Toast.makeText(this@MainActivity, "riprovare chiudi e riapro l'app , pulisci il sensore", Toast.LENGTH_SHORT).show()
                        super.onAuthenticationFailed()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        finishAffinity()

                        super.onAuthenticationError(errorCode, errString)
                    }
                })
        val prontInfo: androidx.biometric.BiometricPrompt.PromptInfo =
            androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle("protezione")
                .setNegativeButtonText("non sono io")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
                .build()
        if (utilsBiometrick.biometricAvailable()) {
            biometricPrompt.authenticate(prontInfo)
        }
    }
}
