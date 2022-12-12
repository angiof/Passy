package passy.prog.utils

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.views.BTnSheetDialogFragment

class FragSheetUtils(fragment: Fragment)  :OnCickFragmentContainer{



    val binding: FragmentContainerBinding? =
        DataBindingUtil.setContentView(fragment.requireActivity(), R.layout.fragment_container)

    fun insert3(){
        binding?.passa = this
    }

    override fun clickInsert() {
        val sheet2 = BTnSheetDialogFragment()
        sheet2.show(sheet2.requireActivity().supportFragmentManager, "sheet")
    }
}