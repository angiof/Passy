package passy.prog.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword

class FragmentContainer : Fragment(R.layout.fragment_container) {
    private var dataSet: MutableList<EntityPassword> = arrayListOf()

    private lateinit var viewModel: ViewModelPassword
    private lateinit var binding: FragmentContainerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val adapter = MyAdapter(object :MyAdapter.OnCardButtonsClick{
           override suspend fun onDelateCard(entityPassword: EntityPassword) {
               GlobalScope.launch {
                   viewModel.cancellaTutto(entityPassword)

               }
           }
       })
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        binding = FragmentContainerBinding.bind(view)
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewModel.lista.observe(requireActivity()) {

            adapter.submitList(it)
        }
        inseriemento()
    }

    private fun inseriemento() {
        binding.fbFrag.apply {
            setOnClickListener {
                GlobalScope.launch {
                    viewModel.insertPasswordViewModel(EntityPassword(0, "surace", "persa"))
                }
            }
        }
    }

    private  fun remove(){

    }
}