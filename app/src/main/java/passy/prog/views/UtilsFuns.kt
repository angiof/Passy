package passy.prog.views

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import passy.prog.viewmodel.ViewModelPassword

fun Context.hideToolbarAndStatusBar(context: Context) {
    (context as MainActivity).supportActionBar!!.hide()
    // Hide the status bar.
    context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
    context.actionBar?.hide()

}
