package passy.prog.views

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import passy.prog.R

open class BTnSheetDialogFragment : BottomSheetDialogFragment() {
    var bottomSheetBehavior: BottomSheetBehavior<*>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val b = inflater.inflate(R.layout.shee_dialog, container, false)



        return b


    }

    override fun onLowMemory() {
        super.onLowMemory()
        Toast.makeText(requireContext(), "sistem error", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        Toast.makeText(requireContext(), "aperto", Toast.LENGTH_SHORT).show()
    }


}


