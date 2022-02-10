package passy.prog.views

import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import passy.prog.viewmodel.ViewModelPassword
import javax.inject.Inject

class DependencesHilt
@Inject
constructor( @ApplicationContext
             val context:Context,
             @FragmentScoped
            val fragment: Fragment
             ){

    fun  showToas(){
      Toast.makeText(context, "hilt", Toast.LENGTH_SHORT).show()
  }

    fun hideStatusBAr(){
        fragment.requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }






}
