package passy.prog.views

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.core.content.ContextCompat
import com.example.passycorefeatures.UtilsXX
import passy.prog.R
import passy.prog.utils.UtilsFuns
import java.util.concurrent.Executor

open class MainActivity : AppCompatActivity() {

    private val utilsFuns by lazy {
        UtilsFuns().PassySettings(this@MainActivity).settingThemeMode()
    }

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
                        Toast.makeText(this@MainActivity, "fatto", Toast.LENGTH_SHORT).show()
                        super.onAuthenticationSucceeded(result)
                    }

                    override fun onAuthenticationFailed() {
                        finishAffinity()
                        Toast.makeText(this@MainActivity, "sto cazzo", Toast.LENGTH_SHORT).show()
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
        if (biometricAvailable()) {
            biometricPrompt.authenticate(prontInfo)
        }
    }

    private fun biometricAvailable(): Boolean {

        val biometricManager = BiometricManager.from(this)
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(applicationContext, "not found HW ", Toast.LENGTH_SHORT).show()
                finish()
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                finish()
                Toast.makeText(applicationContext, "not found HW ", Toast.LENGTH_SHORT).show()
                false
            }
            else -> false
        }
    }
}
