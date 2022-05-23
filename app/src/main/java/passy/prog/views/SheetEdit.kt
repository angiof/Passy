import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import passy.prog.databinding.EditSheetBinding
import passy.prog.views.PersistentData

class BtnSheetEdit : BottomSheetDialogFragment() {
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
        val p = PersistentData()
        val password = p.getParam(requireActivity(), "p").toString()
        val loghin = p.getParam(requireActivity(), "l").toString()




        bindingFragSheet2.txtUser.setText(loghin)
        bindingFragSheet2.txtPassword.setText(password)
        return bindingFragSheet2.root
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
    }


}
