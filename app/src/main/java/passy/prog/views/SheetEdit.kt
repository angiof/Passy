import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import passy.prog.databinding.EditSheetBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.UtilsFuns
import passy.prog.viewmodel.ViewModelPassword
import passy.prog.views.PersistentData
import java.util.*


class BtnSheetEdit : BottomSheetDialogFragment() {
    private lateinit var viewModel: ViewModelPassword
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
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        val data = UtilsFuns().DatePicker().getData()

        val p = PersistentData()
        val descrizione = p.getParam(requireActivity(), "desc").toString()
        val id = p.getParam(requireActivity(), "id")!!.toInt()
        val password = p.getParam(requireActivity(), "p").toString()
        val loghin = p.getParam(requireActivity(), "l").toString()
        val color = p.getParam(requireActivity(), "c").toString().let {
            colorete = if (it.isNullOrEmpty()) {
                null.toString()
            } else {
                it
            }
        }

        bindingFragSheet2.ivRedEdit.setOnClickListener {
            colorete = "r"
        }
        bindingFragSheet2.ivBlue.setOnClickListener {
            colorete = "n"
        }
        bindingFragSheet2.ivGreenEdit.setOnClickListener {
            colorete = "g"
        }
        bindingFragSheet2.desc.setText(descrizione)
        bindingFragSheet2.txtUser.setText(loghin)
        bindingFragSheet2.txtPassword.setText(password)

        bindingFragSheet2.btnSave.setOnClickListener {
            //sett fields
            val labelPassword = bindingFragSheet2.txtPassword.text.toString()
            val labelLoghin = bindingFragSheet2.txtUser.text.toString()
            val descrizione = bindingFragSheet2.desc.text.toString()

            GlobalScope.launch {

                viewModel.updatePassword(
                    EntityPassword(
                        id,
                        descrizione,
                        labelLoghin,
                        labelPassword,
                        colorete,
                        data
                    )
                )
            }
            dismiss()
        }

        return bindingFragSheet2.root
    }

}
