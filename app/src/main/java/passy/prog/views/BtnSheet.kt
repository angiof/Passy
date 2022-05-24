package passy.prog.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import passy.prog.R
import passy.prog.databinding.EditSheetBinding
import passy.prog.databinding.SheeDialogBinding
import passy.prog.databinding.SheetMainBinding
import passy.prog.databinding.SheetPasswordBinding
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword

open class BTnSheetDialogFragment : BottomSheetDialogFragment() {
    var colors: String? = null
    private lateinit var viewModel: ViewModelPassword
    private val bindingFragSheet: SheetMainBinding by lazy {
        SheetMainBinding.inflate(
            layoutInflater
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        saveClick()
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        return bindingFragSheet.root
    }

    private fun bntInserCategory() {


    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveClick() {
        bindingFragSheet.ivRedEdit .setOnClickListener {
            colors = "r"
            bindingFragSheet.btnSave.setBackgroundColor(requireActivity().getColor(R.color.redsoft))
        }
        bindingFragSheet.ivBlue.setOnClickListener {
            colors = "n"
            bindingFragSheet.btnSave.setBackgroundColor(it.context.getColor(R.color.purple_200))
        }
        bindingFragSheet.ivGreenEdit.setOnClickListener {
            colors = "g"
            bindingFragSheet.btnSave.setBackgroundColor(it.context.getColor(R.color.softGreen))
        }
        bindingFragSheet.btnSave.setOnClickListener {
            val loghin = bindingFragSheet.txtUser.text.toString()
            val password = bindingFragSheet.txtPassword.text.toString()
            viewModel.insertPasswordViewModel(EntityPassword(0, loghin, password, colors, null))
            viewModel.mostraToas(requireActivity(), loghin + password)
        }
    }

    private fun onChangeAvatar() {
        with(bindingFragSheet.ivAvatarSheetN) {
            this.setOnClickListener {
                showMenu(it, R.menu.menu_avatar)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.personale -> {
                    Toast.makeText(requireContext(), "cambia", Toast.LENGTH_SHORT).show()
                }
                R.id.spento -> {
                    // bindingFragSheet.ivAvatarSheet.visibility=View.GONE
                    bindingFragSheet.ivAvatarSheetN.background =
                        requireActivity().getDrawable(R.drawable.ic_lock)
                }
            }
            true
        }
        // Show the popup menu.
        popup.show()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun SetColorOnclickListener(): String? {
        with(bindingFragSheet){
            this.ivRedEdit.setOnClickListener {
                Toast.makeText(it.context, "premuto", Toast.LENGTH_SHORT).show()
            }
        }
        return colors
    }
}


