package passy.prog.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.databinding.SheeDialogBinding
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword
import com.google.android.material.shape.CornerFamily

import android.R




class FragmentContainer : Fragment(passy.prog.R.layout.fragment_container) {
    private val btnSheetDialog: BottomSheetDialog by lazy { BottomSheetDialog(requireActivity()) }
    private lateinit var viewModel: ViewModelPassword
    private lateinit var binding: FragmentContainerBinding
    private val viewSheetBin: SheeDialogBinding by lazy { SheeDialogBinding.inflate(layoutInflater) }
    val languages by lazy { resources.getStringArray(passy.prog.R.array.colori_spinner) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun OpenShowSheetButon(entityPassword: EntityPassword) {
                Log.d("mario", "${entityPassword.color}+ ${Thread.currentThread().name}")

                showSheet(entityPassword = entityPassword)

            }

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
        hideFabs()

    }

    private fun inseriemento() {
        binding.fbFrag.apply {
            setOnClickListener {
                GlobalScope.launch {
                    viewModel.insertPasswordViewModel(EntityPassword(0, "surace", "persa", "r"))
                }
            }
        }
        binding.floatingActionButton.visibility = View.GONE

    }

    private fun hideFabs() {
        binding.fbFrag.setOnLongClickListener {
            if (binding.floatingActionButton.visibility == View.VISIBLE) {
                binding.floatingActionButton.visibility = View.GONE

            } else if (binding.floatingActionButton.visibility == View.GONE) {
                binding.floatingActionButton.visibility = View.VISIBLE

            }

            return@setOnLongClickListener true
        }
    }

    private fun showSheet(entityPassword: EntityPassword) {
        MaterialDialog(requireContext(), BottomSheet()).show {
            cornerRadius(16f)
            customView(view = viewSheetBin.root, scrollable = true)
            title(passy.prog.R.string.insertitle)
            viewSheetBin.passwordInput.setOnClickListener {
            }
            with(viewSheetBin.spinner){
                this.prompt=getString(passy.prog.R.string.app_name)
                onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            Toast.makeText(requireContext(), languages[position], Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
            }
            positiveButton {
            }
            negativeButton {

            }

        }
        with(viewSheetBin.ivAvatarSheet){
            val radius = resources.getDimension(passy.prog.R.dimen.md_dialog_default_corner_radius)
            this.setShapeAppearanceModel(
                this.shapeAppearanceModel
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build()
            )
        }


    }


}

