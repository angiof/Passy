package passy.prog.views

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
import passy.prog.databinding.EditSheetBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.*
import passy.prog.viewmodel.ViewModelPassword


class BtnSheetEdit : BottomSheetDialogFragment(), OnClickCheet {
    private val viewModel: ViewModelPassword by viewModels()
    private  var colorete: String? = null
    private val bindingFragSheet2: EditSheetBinding by lazy {
        EditSheetBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragSheet2.red = this
        bindingFragSheet2.giallo = this
        bindingFragSheet2.verde = this
        bindingFragSheet2.save = this
        setFields(bindingFragSheet2)
        return bindingFragSheet2.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnRedCirlce() {
        colorete = ROSSO
        bindingFragSheet2.run {
            txtPassword.setTextColor(requireContext().getColor(R.color.griggio_materil))
            txtUser.setTextColor(requireContext().getColor(R.color.griggio_materil))
            desc.setTextColor(requireContext().getColor(R.color.griggio_materil))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnYelowCirlce() {
        colorete = BLUE
        bindingFragSheet2.run {
            txtPassword.setTextColor(requireContext().getColor(R.color.materail_blue))
            txtUser.setTextColor(requireContext().getColor(R.color.materail_blue))
            desc.setTextColor(requireContext().getColor(R.color.materail_blue))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnGreenCirlce() {
        colorete = VERDE
        bindingFragSheet2.run {
            txtPassword.setTextColor(requireContext().getColor(R.color.softGreen2))
            txtUser.setTextColor(requireContext().getColor(R.color.softGreen2))
            desc.setTextColor(requireContext().getColor(R.color.softGreen2))
        }
    }

    override fun btnSave() {

        val labelPassword = bindingFragSheet2.txtPassword.text.toString()
        val labelLoghin = bindingFragSheet2.txtUser.text.toString()
        val descrizione = bindingFragSheet2.desc.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            if (UtilsFuns.PassyCheckers()
                    .onPasswordCheck(
                        ctx = requireContext(),
                        password = labelPassword
                    )
            ) {
                viewModel.updatePassword(
                    EntityPassword(
                        id = setId(),
                        descrizione = descrizione,
                        loghin = labelLoghin,
                        password = labelPassword,
                        color = colorete?: VERDE,
                        data = UtilsFuns().DatePicker().getData()
                    )
                )
            }
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bindingFragSheet2.txtPassword.onEditor(bindingFragSheet2.txtPassword)
    }
}

