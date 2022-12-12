package passy.prog.views

import android.annotation.SuppressLint
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


class BtnSheetEdit : BottomSheetDialogFragment(), OncllickBtnSheetEdit {
    private val viewModel: ViewModelPassword by viewModels()
    private lateinit var colorete: String
    private val bindingFragSheet2: EditSheetBinding by lazy {
        EditSheetBinding.inflate(
            layoutInflater
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragSheet2.red = this
        bindingFragSheet2.giallo = this
        bindingFragSheet2.verde = this
        bindingFragSheet2.save = this

        val p = PersistentData()
        val descrizione = p.getParam(requireActivity(), "desc").toString()
        val id = p.getParam(requireActivity(), "id")!!.toInt()
        val password = p.getParam(requireActivity(), "p").toString()
        val loghin = p.getParam(requireActivity(), "l").toString()
        p.getParam(requireActivity(), "c").toString().let {
            colorete = it.ifEmpty {
                null.toString()
            }
        }

        bindingFragSheet2.desc.setText(descrizione)
        bindingFragSheet2.txtUser.setText(loghin)
        bindingFragSheet2.txtPassword.setText(password)

        //sett fields
        return bindingFragSheet2.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnRedCirlce() {
        colorete = ROSSO
        bindingFragSheet2.run {
            txtPassword.setTextColor(requireContext().getColor(R.color.redsoft2))
            txtUser.setTextColor(requireContext().getColor(R.color.redsoft2))
            desc.setTextColor(requireContext().getColor(R.color.redsoft2))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun btnYelowCirlce() {
        colorete = ARANCIA
        bindingFragSheet2.run {
            txtPassword.setTextColor(requireContext().getColor(R.color.materialonrange))
            txtUser.setTextColor(requireContext().getColor(R.color.materialonrange))
            desc.setTextColor(requireContext().getColor(R.color.materialonrange))
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
                        id = id,
                        descrizione = descrizione,
                        loghin = labelLoghin,
                        password = labelPassword,
                        color = colorete,
                        data = UtilsFuns().DatePicker().getData()
                    )
                )
            }
            dismiss()
        }
    }
}

