package passy.prog.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import passy.prog.R
import passy.prog.db.EntityPassword
import passy.prog.views.MainActivity
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
        // Hide the status bar.
        context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        context.actionBar?.hide()


    }


    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun showDialogSettings(context: Context, entityPassword: EntityPassword) = runBlocking {

        val viewDialog = View.inflate(context, R.layout.custom_, null)
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(viewDialog)
        val dialog = builder.create()
        viewDialog.findViewById<View>(R.id.edit).setOnClickListener {

            Log.d("dialog", "premuto edit ")
            dialog.dismiss()
        }
        dialog.show()
    }


    inner class AdapterFuns {

        fun setDefaultAvatar(ImageView: ImageView): Any =
            ImageView.setBackgroundResource(R.drawable.ic_vpn)

        fun setAssets(entityPassword: EntityPassword, imageView: ImageView) {
            if (entityPassword.loghin!!.contains("accenture", true) or
                entityPassword.descrizione!!.contains("accenture")
            ) {
                imageView.setBackgroundResource(R.drawable.ic_acure_icon)
            } else if (entityPassword.loghin.contains("microsoft", true)
                or (entityPassword.loghin.contains("live")
                        or entityPassword.loghin.contains("hotmail"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_microsoft)
            } else if (entityPassword.loghin.contains("ig", true) ||
                entityPassword.descrizione.contains("ig")
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_instagram)
            } else if (entityPassword.loghin.contains(
                    "fb",
                    true
                ) or (entityPassword.descrizione.contains("facebook", true))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
            } else if (entityPassword.descrizione.contains("git", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_github)
            } else if (entityPassword.descrizione?.contains("gitlab", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_gitlab)
            } else if (entityPassword.loghin.contains("apple", true) or
                entityPassword.loghin.contains("icloud", true) or
                (entityPassword.descrizione?.contains("apple") == true or
                        (entityPassword.descrizione?.contains("iclaud", true)) or
                        (entityPassword.descrizione?.contains("mac")) or
                        (entityPassword.descrizione?.contains("iphone", true))
                        )
            ) {
                imageView.setBackgroundResource(R.drawable.ic_apple_brands)
            } else if (entityPassword.loghin.contains("android", true) or
                (entityPassword.descrizione?.contains("telefono"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_android_brands)
            } else if (entityPassword.descrizione.contains("paypal", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_paypal)
            } else if (entityPassword.loghin.contains("koltin", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_kotlin)
            } else if (entityPassword.descrizione.contains("oracle", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_java)
            } else if (entityPassword.loghin.contains("google", true) or
                (entityPassword.descrizione.contains("gmail"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_google_svgrepo_com)
            }
        }

    }

    inner class PassyFeatures {

        fun onChangeColorFields(
            color: Color?, tv_log: Loghin, tv_password: Password,
            tv_des: Descrizione
        ) {
            when (color) {
                "r" -> {
                    tv_log.setTextColor(tv_log.context.resources.getColor(R.color.redsoft2))
                    tv_password.setTextColor(tv_log.context.resources.getColor(R.color.redsoft2))
                    tv_password.setTextColor(tv_log.context.resources.getColor(R.color.redsoft2))
                    tv_log.setTextColor(tv_log.context.resources.getColor(R.color.redsoft2))
                    tv_log.setTextColor(tv_log.context.resources.getColor(R.color.redsoft2))
                }
                "g" -> {
                    tv_des.setTextColor(tv_log.context.resources.getColor(R.color.softGreen2))
                    tv_password.setTextColor(tv_log.context.resources.getColor(R.color.softGreen2))
                    tv_des.setTextColor(tv_log.context.resources.getColor(R.color.softGreen2))
                }
                "n" -> {
                    tv_des.setTextColor(tv_log.context.resources.getColor(R.color.materialonrange))
                    tv_password.setTextColor(tv_log.context.resources.getColor(R.color.materialonrange))
                    tv_des.setTextColor(tv_log.context.resources.getColor(R.color.materialonrange))
                }
            }
        }
    }

    class PassyCheckers {


        fun onPasswordCheck(ctx: Context, password: String, loghin: String): Boolean =
            runBlocking(Dispatchers.Main) {
                return@runBlocking if (password.isEmpty() and password.isEmpty()) {
                    Toast.makeText(
                        ctx,
                        "password or loghin cannot be empaty",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                } else {
                    true
                }
            }
    }

    open class FragReciverSettings(val fragment: Fragment)


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

    inner class PassySettings(val ctx: Context) {

        private fun getThemeClor() {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        }

        fun settingThemeMode() {
            when (ctx.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    getThemeClor()
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    getThemeClor()
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    getThemeClor()
                }
            }
        }
    }

    inner class DatePicker {

        private fun getDateString(date: Date?, format: String?, locale: Locale?): String? {
            val formatter: DateFormat = SimpleDateFormat(format, locale)
            return date?.let { formatter.format(it) }
        }

        fun getData(): String? {
            val data = UtilsFuns().DatePicker().getDateString(Date(), "dd/MM/yyyy", Locale.ITALY)
                .toString()
            val time =
                UtilsFuns().DatePicker().getDateString(Date(), "HH:mm", Locale.ITALY).toString()
            return "$data/$time"
        }


    }

}
typealias Loghin = TextInputEditText
typealias Password = TextInputEditText
typealias Descrizione = TextInputEditText
typealias Color = String?



