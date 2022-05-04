package passy.prog.views

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import passy.prog.R
import passy.prog.db.EntityPassword

typealias utils = UtilsFuns

class UtilsFuns {

    fun Context.hideToolbarAndStatusBar(context: Context) {
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

}
