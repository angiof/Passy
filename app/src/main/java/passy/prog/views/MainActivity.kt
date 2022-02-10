package passy.prog.views

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import passy.prog.R
import passy.prog.viewmodel.ViewModelPassword

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val fragVisibility: View? by lazy { findViewById(R.id.fragmentContainerView2) }
    var cancellationSignal: CancellationSignal? = null
    val auteticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(this@MainActivity, "un cazzo ", Toast.LENGTH_SHORT).show()
                        fragVisibility?.visibility = View.VISIBLE


                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        Toast.makeText(
                            this@MainActivity,
                            "$errorCode +$errString",
                            Toast.LENGTH_SHORT
                        ).show()

                        super.onAuthenticationError(errorCode, errString)

                    }

                }
            } else {
                TODO("VERSION.SDK_INT < P")
            }
    private lateinit var viewModel: ViewModelPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.hideToolbarAndStatusBar(this)
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        idFInger()
        super.onResume()
    }


    private fun checkBiometriSUpport(): Boolean {
        val keyWardManger = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyWardManger.isKeyguardSecure) {
            Toast.makeText(this, "non è sato abilitato ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }

        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    private fun getCancelTIonSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {

        }
        return cancellationSignal as CancellationSignal
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun idFInger() {
        val biometricPrompt = BiometricPrompt.Builder(this).let {
            title = "identificatore  impronte "
            it.setTitle("titlo")
            it.setDescription("cerchiamo di prottegerre i tuoi dati")
            it.setNegativeButton("cancel", this.mainExecutor, { dialog, which ->
                Toast.makeText(this, "cancellao da te", Toast.LENGTH_SHORT).show()
                fragVisibility?.visibility = View.GONE

            })
            it.build()
        }
        biometricPrompt.authenticate(getCancelTIonSignal(), mainExecutor, auteticationCallback)
    }


}