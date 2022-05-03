package passy.prog.views

import android.app.Activity
import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.text.TextUtils.replace
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.hideToolbarAndStatusBar(context: Context) {
    (context as MainActivity).supportActionBar!!.hide()
    // Hide the status bar.
    context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
    context.actionBar?.hide()

}


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}



