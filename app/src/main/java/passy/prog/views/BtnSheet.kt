package passy.prog.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import passy.prog.R
import passy.prog.databinding.SheeDialogBinding
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword

open class BTnSheetDialogFragment : BottomSheetDialogFragment() {
    var colors: String? = null
    private lateinit var viewModel: ViewModelPassword
    private val bindingFragSheet: SheeDialogBinding by lazy {
        SheeDialogBinding.inflate(
            layoutInflater
        )
    }

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
        bindingFragSheet.bntInsertCategory.setOnClickListener {
            Toast.makeText(requireActivity(), "feature da lavorare", Toast.LENGTH_SHORT).show()
            // it.visibility = View.GONE
            // bindingFragSheet.ivAvatarSheetN.visibility = View.VISIBLE
        }

    }
    private fun saveClick() {

        bindingFragSheet.btnSave.setOnClickListener {
            val loghin = bindingFragSheet.txtUser.text.toString()
            val password = bindingFragSheet.txtPassword.text.toString()
            viewModel.insertPasswordViewModel(EntityPassword(0, "null", "password", null, null))
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
                    bindingFragSheet.bntInsertCategory.visibility = View.VISIBLE
                }
            }
            true
        }
        // Show the popup menu.
        popup.show()
    }
}


