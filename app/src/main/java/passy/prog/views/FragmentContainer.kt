package passy.prog.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.databinding.SheeDialogBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.utils
import passy.prog.viewmodel.ViewModelPassword
import passy.prog.views.adapter.MyAdapter

class FragmentContainer : Fragment(R.layout.fragment_container) {

    private lateinit var viewModel: ViewModelPassword
    private lateinit var binding: FragmentContainerBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val utils: utils = utils()
        utils.hideToolbarAndStatusBar(view.context)

        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        binding = FragmentContainerBinding.bind(view)

        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun openShowSheetButon(entityPassword: EntityPassword) {
                //showSheet()
            }

            @SuppressLint("SetTextI18n")
            override suspend fun onUpdatePassword(entityPassword: EntityPassword) {

                // dialogs(entityPassword)
                val sheet2 = BtnSheetEdit()

                val p = PersistentData()

                p.saveParam(requireActivity(), "id", entityPassword.id)
                p.saveParam(requireActivity(), "l", entityPassword.loghin)
                p.saveParam(requireActivity(), "p", entityPassword.password)
                p.saveParam(requireActivity(), "c", entityPassword.color)
                p.saveParam(requireActivity(), "desc", entityPassword.descrizione)
                sheet2.show(requireActivity().supportFragmentManager, "sheet2")


                //  fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView2, sheet2)?.commit()


            }

            // viewModel.updatePassword(entityPassword)


            override suspend fun onDelateCard(entityPassword: EntityPassword) {
                viewModel.cancellaTutto(entityPassword)
            }

        }, requireActivity())
        binding = FragmentContainerBinding.bind(view)
        binding.recyclerView.apply {
            val decorationSpan = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
            addItemDecoration(decorationSpan)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        ViewModelProvider(this)[ViewModelPassword::class.java]
        viewModel.lista.observe(requireActivity()) {
            adapter.submitList(it)
            if (it.size == 0) {
                binding.lottie0.visibility = View.VISIBLE
            } else {
                binding.lottie0.visibility = View.GONE
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fabInsert() {
        binding.fbFrag.setOnClickListener {
            val sheet2 = BTnSheetDialogFragment()
            sheet2.show(requireActivity().supportFragmentManager, "sheet")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        fabInsert()
    }

}