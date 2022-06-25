package passy.prog.utils

import android.app.Activity
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import passy.prog.R
import passy.prog.db.EntityPassword
import passy.prog.views.MainActivity
import java.time.LocalDateTime

typealias utils = UtilsFuns

fun pipo() {
    println("questo è pippo ")
}

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

            launch {
                // a.delateLisatiner(entityPassword)

            }
            Log.d("dialog", "premuto edit ")
            dialog.dismiss()
        }
        dialog.show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    open fun getdataFromDevice(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val myLdt = LocalDateTime.of(year, month, day, hour, minute)
        return myLdt.toString()
    }

    inner class AdapterFuns() {

        fun setDefaultAvatar(ImageView: ImageView): Any =
            ImageView.setBackgroundResource(R.drawable.ic_google_svgrepo_com)

        fun setAssets(entityPassword: EntityPassword, imageView: ImageView) {
            if (entityPassword.loghin!!.contains("accenture", true)) {
                imageView.setBackgroundResource(R.drawable.ic_acure_icon)
            } else if (entityPassword.loghin.contains("microsoft", true)
                or (entityPassword.loghin.contains("live")
                or entityPassword.loghin.contains("hotmail"))
            ) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_microsoft)
            } else if (entityPassword.loghin.contains("ig", true) ||
                        entityPassword.descrizione?.contains("ig") == true)
                     {
                imageView.setBackgroundResource(R.drawable.ic_icons8_instagram)
            } else if (entityPassword.loghin.contains("fb", true) or (entityPassword.descrizione?.contains("facebook",true) == true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
            } else if (entityPassword.loghin.contains("git", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_github)
            } else if (entityPassword.loghin.contains("gitlab", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_gitlab)
            } else if (entityPassword.loghin.contains("apple", true)) {
                imageView.setBackgroundResource(R.drawable.ic_apple_brands)
            } else if (entityPassword.loghin.contains("android", true)) {
                imageView.setBackgroundResource(R.drawable.ic_android_brands)
            } else if (entityPassword.descrizione!!.contains("paypal", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_paypal)
            } else if (entityPassword.loghin.contains("koltin", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_kotlin)
            } else if (entityPassword.loghin.contains("oracle", true)) {
                imageView.setBackgroundResource(R.drawable.ic_icons8_java)
            }else if (entityPassword.loghin.contains("google",true) or
                (entityPassword.descrizione.contains("gmail"))) {
                imageView.setBackgroundResource(R.drawable.ic_google_svgrepo_com)
            }
        }

    }

    inner class PassyFeatures() {

    }

}

