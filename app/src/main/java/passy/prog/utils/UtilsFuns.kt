package passy.prog.utils

import android.app.Activity
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import passy.prog.R
import passy.prog.db.EntityPassword
import passy.prog.views.MainActivity
import java.time.LocalDateTime

typealias utils = UtilsFuns
fun pipo(){
    println("questo è pippo ")
}

class UtilsFuns {

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
fun pipi(){
    pipo()
}

    @RequiresApi(Build.VERSION_CODES.O)
  open  fun getdataFromDevice(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        var myLdt = LocalDateTime.of(year, month, day, hour, minute)
        return myLdt.toString()
    }
}

