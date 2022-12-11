package passy.prog.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.db.EntityPassword
import passy.prog.utils.UtilsFuns
import passy.prog.viewmodel.ViewModelPassword
import passy.prog.views.adapter.MyAdapter

class FragmentContainer : Fragment(R.layout.fragment_container) {
    private val viewModel: ViewModelPassword by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UtilsFuns().hideToolbarAndStatusBar(requireActivity())
        val binding: FragmentContainerBinding? =
            DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_container)

            binding?.fab = this


        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun openShowSheetButon(entityPassword: EntityPassword) {
            }

            @SuppressLint("SetTextI18n")
            override suspend fun onUpdatePassword(entityPassword: EntityPassword) {

                UtilsFuns.FragemntSheetSendEntiti(
                    this@FragmentContainer
                ).sender(entityPassword)
            }

            override suspend fun onDelateCard(entityPassword: EntityPassword) {
                viewModel.cancellaTutto(entityPassword)
            }

        }, requireActivity())
        binding!!.recyclerView.apply {
            val decorationSpan = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
            addItemDecoration(decorationSpan)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewModel.lista.observe(requireActivity()) {
            adapter.submitList(it)
            if (it.size == 0) {
                binding.lottie0.visibility = View.VISIBLE
            } else {
                binding.lottie0.visibility = View.GONE
            }
        }
    }

    fun fabInsert() {
        val sheet2 = BTnSheetDialogFragment()
        sheet2.show(requireActivity().supportFragmentManager, "sheet")

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
    }
}