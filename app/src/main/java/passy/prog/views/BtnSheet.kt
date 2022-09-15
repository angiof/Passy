package passy.prog.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import passy.prog.R
import passy.prog.databinding.SheetMainBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.ARANCIA
import passy.prog.utils.ROSSO
import passy.prog.utils.UtilsFuns
import passy.prog.utils.VERDE
import passy.prog.viewmodel.ViewModelPassword


open class BTnSheetDialogFragment : BottomSheetDialogFragment() {
    var colors: String? = null
    var frag: Fragment? = null

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
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        saveClick()
        return bindingFragSheet.root
    }

    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveClick() {
        bindingFragSheet.ivBlue.setOnClickListener { n ->
            colors = ARANCIA
            if (colors == ARANCIA) {
                bindingFragSheet.run {
                    txtPassword.setTextColor(n.context.getColor(R.color.materialonrange))
                    edDescrizione.setTextColor(n.context.getColor(R.color.materialonrange))
                    txtUser.setTextColor(n.context.getColor(R.color.materialonrange))
                }
            }
        }
        bindingFragSheet.ivGreenEdit.setOnClickListener { g ->
            colors = VERDE
            with(bindingFragSheet) {
                txtPassword.setTextColor(requireContext().getColor(R.color.softGreen))
                edDescrizione.setTextColor(g.context.getColor(R.color.softGreen))
                txtUser.setTextColor(g.context.getColor(R.color.softGreen))
            }
        }
        bindingFragSheet.ivRedEdit.setOnClickListener {
            colors = ROSSO
            if (colors == ROSSO) {
                bindingFragSheet.run {
                    txtPassword.setTextColor(it.context.getColor(R.color.redsoft2))
                    edDescrizione.setTextColor(it.context.getColor(R.color.redsoft2))
                    txtUser.setTextColor(it.context.getColor(R.color.redsoft2))
                }
            }

        }
        bindingFragSheet.btnSave.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val data = UtilsFuns().DatePicker().getData()
                val descrizione = bindingFragSheet.edDescrizione.text.toString()
                val loghin = bindingFragSheet.txtUser.text.toString()
                val password = bindingFragSheet.txtPassword.text.toString()
                if (UtilsFuns.PassyCheckers()
                        .onPasswordCheck(it.context, password = password)
                ) {
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

    override fun onAttach(context: Context) {
        frag = this
        super.onAttach(context)
    }
}


