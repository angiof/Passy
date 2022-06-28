package passy.prog.views

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import passy.prog.databinding.SheetMainBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.UtilsFuns
import passy.prog.viewmodel.ViewModelPassword
import java.util.*

open class BTnSheetDialogFragment : BottomSheetDialogFragment() {
    var colors: String? = null
    private val utilsFuns :UtilsFuns = UtilsFuns()
    private lateinit var viewModel: ViewModelPassword
    private val bindingFragSheet: SheetMainBinding by lazy {
        SheetMainBinding.inflate(
            layoutInflater
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        saveClick()
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        return bindingFragSheet.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveClick() {
        bindingFragSheet.ivRedEdit .setOnClickListener {
            colors = "r"
        }
        bindingFragSheet.ivBlue.setOnClickListener {
            colors = "n"
        }
        bindingFragSheet.ivGreenEdit.setOnClickListener {
            colors = "g"
        }
        bindingFragSheet.btnSave.setOnClickListener {

            val data = UtilsFuns().DatePicker().getData()
            val descrizione=bindingFragSheet.edDescrizione.text.toString()
            val loghin = bindingFragSheet.txtUser.text.toString()
            val password = bindingFragSheet.txtPassword.text.toString()
            if (UtilsFuns.PassyCheckers().onPasswordCheck(it.context, password = password,loghin)){
                viewModel.insertPasswordViewModel(
                    EntityPassword(
                        0, descrizione, loghin, password, colors, data.toString()
                    )
                )

            }
            dismiss()
        }
    }


}


