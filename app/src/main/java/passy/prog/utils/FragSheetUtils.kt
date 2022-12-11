package passy.prog.utils

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.views.BTnSheetDialogFragment
import passy.prog.views.FragmentContainer

class FragSheetUtils(fragment: Fragment) {

     fun fabInsert() {
        val sheet2 = BTnSheetDialogFragment()
        sheet2.show(sheet2.requireActivity().supportFragmentManager, "sheet")
    }

    val binding: FragmentContainerBinding? =
        DataBindingUtil.setContentView(fragment.requireActivity(), R.layout.fragment_container)

    fun insert3(){
        binding?.passa = this
        fabInsert()
    }
}