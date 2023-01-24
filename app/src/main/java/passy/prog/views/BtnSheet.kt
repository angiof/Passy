package passy.prog.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import passy.prog.R
import passy.prog.databinding.SheetMainBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.*
import passy.prog.viewmodel.ViewModelPassword


open class BTnSheetDialogFragment : BottomSheetDialogFragment(), OnClickCheetInsert {
    var colors: String? = null
    private val viewModel: ViewModelPassword by viewModels()
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
        bindingFragSheet.onClicks = this
        return bindingFragSheet.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnRedCirlce() {
        colors = ROSSO
        if (colors == ROSSO) {
            bindingFragSheet.run {
                txtPassword.setTextColor(requireContext().getColor(R.color.griggio_materil))
                edDescrizione.setTextColor(requireContext().getColor(R.color.griggio_materil))
                txtUser.setTextColor(requireContext().getColor(R.color.griggio_materil))
                changeStartIcon(
                    txtPassword = bindingFragSheet.txtPass,
                    txtUser = txtLoghin,
                    txtDescrizione = descrzioneLayout,
                    colors = colors ?: "",
                    this@BTnSheetDialogFragment
                )

            }
        }
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnYelowCirlce() {
        colors = BLUE
        if (colors == BLUE) {
            bindingFragSheet.run {
                txtPassword.setTextColor(requireContext().getColor(R.color.materail_blue_text))
                edDescrizione.setTextColor(requireContext().getColor(R.color.materail_blue_text))
                txtUser.setTextColor(requireContext().getColor(R.color.materail_blue_text))
                changeStartIcon(
                    txtPassword = bindingFragSheet.txtPass,
                    txtUser = txtLoghin,
                    txtDescrizione = descrzioneLayout,
                    colors = colors ?: "",
                    this@BTnSheetDialogFragment
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnGreenCirlce() {
        colors = VERDE
        with(bindingFragSheet) {
            txtPassword.setTextColor(requireContext().getColor(R.color.softGreen))
            edDescrizione.setTextColor(requireContext().getColor(R.color.softGreen))
            txtUser.setTextColor(requireContext().getColor(R.color.softGreen))
            changeStartIcon(
                txtPassword = bindingFragSheet.txtPass,
                txtUser = txtLoghin,
                txtDescrizione = descrzioneLayout,
                colors = colors ?: "",
                this@BTnSheetDialogFragment
            )
        }
    }

    override fun btnSave() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = UtilsFuns().DatePicker().getData()
            val descrizione = bindingFragSheet.edDescrizione.text.toString()
            val loghin = bindingFragSheet.txtUser.text.toString()
            val password = bindingFragSheet.txtPassword.text.toString()
            if (UtilsFuns.PassyCheckers()
                    .onPasswordCheck(requireContext(), password = password)
            ) {
                viewModel.insertPasswordViewModel(
                    entityPassword = EntityPassword(
                        0,
                        descrizione = descrizione,
                        loghin = loghin,
                        password = password,
                        color = colors,
                        data = data
                    )
                )
            }
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bindingFragSheet.txtPassword.onEditor(bindingFragSheet.txtPassword)
    }

}


