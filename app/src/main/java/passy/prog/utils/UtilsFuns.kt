package passy.prog.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import passy.prog.R
import passy.prog.db.EntityPassword
import passy.prog.views.BtnSheetEdit
import passy.prog.views.MainActivity
import passy.prog.views.PersistentData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

typealias utils = UtilsFuns

//colors vals
const val ROSSO: String = "Rosso"
const val VERDE: String = "Verde"
const val ARANCIA: String = "Arancia"

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
                (entityPassword.descrizione?.contains("apple") == true or
                        (entityPassword.descrizione?.contains("iclaud", true)) or
                        (entityPassword.descrizione?.contains("mac")) or
                        (entityPassword.descrizione?.contains("iphone", true)))
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
            return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
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

     class FragemntSheetSendEntiti(val ctx: Fragment) {
        fun sender(entityPassword: EntityPassword) {
            val sheet2 = BtnSheetEdit()
            val p = PersistentData()
            p.saveParam(ctx.requireActivity(), "id", entityPassword.id)
            p.saveParam(ctx.requireActivity(), "l", entityPassword.loghin)
            p.saveParam(ctx.requireActivity(), "p", entityPassword.password)
            p.saveParam(ctx.requireActivity(), "c", entityPassword.color)
            p.saveParam(ctx.requireActivity(), "desc", entityPassword.descrizione)
            sheet2.show(ctx.requireActivity().supportFragmentManager, "sheet2")
        }
    }
}




