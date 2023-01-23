package passy.prog.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import passy.prog.R
import passy.prog.db.EntityPassword
import passy.prog.root.DeviceUtils
import passy.prog.views.MainActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

//colors vals
const val ROSSO: String = "Rosso"
const val VERDE: String = "Verde"
const val BLUE: String = "Arancia"

open class UtilsFuns {

    fun hideToolbarAndStatusBar(context: Context) {
        (context as MainActivity).supportActionBar!!.hide()
        context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        context.actionBar?.hide()
    }

    inner class AdapterFuns {

        fun setDefaultAvatar(ImageView: ImageView): Any =
            ImageView.setBackgroundResource(R.drawable.ic_vpn)

        fun setAssets(entityPassword: EntityPassword, imageView: ImageView) {
            if (entityPassword.loghin!!.contains(
                    "accenture",
                    true
                ) or entityPassword.descrizione!!.contains("accenture")
            ) {
                imageView.setBackgroundResource(R.drawable.ic_acure_icon)
            } else if (entityPassword.loghin.contains(
                    "microsoft",
                    true
                ) or (entityPassword.loghin.contains("live") or
                        entityPassword.loghin.contains("hotmail"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_microsoft)
            } else if (entityPassword.loghin.contains(
                    "ig",
                    true
                ) || entityPassword.descrizione.contains("ig")
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_instagram)
            } else if (entityPassword.loghin.contains(
                    "fb", true
                ) or (entityPassword.descrizione.contains("facebook", true))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
            } else if (entityPassword.descrizione.contains("git", true)) {
                if (entityPassword.descrizione.contains("lab")) {
                    imageView.setBackgroundResource(R.drawable.ic_icons8_gitlab)
                } else {
                    imageView.setBackgroundResource(R.drawable.ic_icons8_github)
                }
            } else if (entityPassword.loghin.contains("apple", true) or
                entityPassword.loghin.contains("icloud", true) or
                (entityPassword.descrizione.contains("apple") == true or
                        (entityPassword.descrizione.contains("iclaud", true)) or
                        (entityPassword.descrizione.contains("mac")) or
                        (entityPassword.descrizione.contains("iphone", true)))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_apple_brands)
            } else if (entityPassword.loghin.contains("android", true) or
                (entityPassword.descrizione?.contains("telefono"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_android_brands)
            } else if (entityPassword.descrizione.contains("paypal", true) and
                (entityPassword.loghin?.contains("@"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_paypal)
            } else if (entityPassword.loghin.contains("kotlin", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_kotlin)
            } else if (entityPassword.descrizione.contains("oracle", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_java)
            } else if (entityPassword.loghin.contains("google", true) or
                (entityPassword.descrizione.contains("gmail"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_google_svgrepo_com)
            } else if (entityPassword.descrizione.contains("italo")) {
                imageView.setBackgroundResource(R.drawable.ic_italo)
            }
        }
    }

    class PassyCheckers {

        fun onPasswordCheck(ctx: Context, password: String): Boolean =
            runBlocking(Dispatchers.Main) {
                return@runBlocking if (password.isEmpty() and password.isEmpty()) {
                    Toast.makeText(
                        ctx, "password or loghin cannot be empaty", Toast.LENGTH_SHORT
                    ).show()
                    false
                } else {
                    true
                }
            }
    }

    open class PassyCheckersBiometrick(val ctx: Activity) {

        fun biometricAvailable(): Boolean {
            val biometricManager = BiometricManager.from(ctx)
            return when (biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
            )) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    true
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Toast.makeText(ctx, "not found HW ", Toast.LENGTH_SHORT).show()
                    ctx.finish()
                    false
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    ctx.finish()
                    Toast.makeText(ctx, "not found HW ", Toast.LENGTH_SHORT).show()
                    false
                }
                else -> false
            }
        }
    }

    inner class DatePicker {

        private fun getDateString(date: Date?, format: String?, locale: Locale?): String? {
            val formatter: DateFormat = SimpleDateFormat(format, locale)
            return date?.let { formatter.format(it) }
        }

        fun getData(): String {
            val data = UtilsFuns().DatePicker().getDateString(Date(), "dd/MM/yyyy", Locale.ITALY)
                .toString()
            val time =
                UtilsFuns().DatePicker().getDateString(Date(), "HH:mm", Locale.ITALY).toString()
            return "$data/$time"
        }
    }


}


fun MainActivity.face() {
    val executor: Executor = ContextCompat.getMainExecutor(this)
    val biometricPrompt: androidx.biometric.BiometricPrompt =
        androidx.biometric.BiometricPrompt(
            this,
            executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationFailed() {
                    finishAffinity()
                    Toast.makeText(
                        applicationContext,
                        "riprovare chiudi e riapro l'app , pulisci il sensore",
                        Toast.LENGTH_SHORT
                    ).show()
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    finishAffinity()

                    super.onAuthenticationError(errorCode, errString)
                }
            })
    val prontInfo: androidx.biometric.BiometricPrompt.PromptInfo =
        androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle("protezione")
            .setNegativeButtonText("non sono io")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()
    if (utilsBiometrick.biometricAvailable()) {
        biometricPrompt.authenticate(prontInfo)
    }
}


fun blockScreenShots(mainActivity: MainActivity) {
    mainActivity.window.setFlags(
        WindowManager.LayoutParams.FLAG_SECURE,
        WindowManager.LayoutParams.FLAG_SECURE
    )

}

fun MainActivity.checkRootAndCloseApp(mainActivity: MainActivity) {
    if (DeviceUtils().isRootAvailable()) {
        Toast.makeText(mainActivity, getString(R.string.device_root),
            Toast.LENGTH_SHORT).show()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    } else {
        Toast.makeText(mainActivity, "free root", Toast.LENGTH_SHORT).show()
        Log.d("free","${DeviceUtils().isRootAvailable()}")
    }
}










