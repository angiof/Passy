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
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint
import kotlinx.coroutines.*
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.databinding.SheeDialogBinding
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword
import javax.inject.Inject

@AndroidEntryPoint
class FragmentContainer : Fragment(passy.prog.R.layout.fragment_container) {
    @Inject
    lateinit var dependencesHilt: DependencesHilt

    //curoines
    private val coroutineScopeInsert: CoroutineScope by lazy { CoroutineScope(Dispatchers.Default) }
    private lateinit var viewModel: ViewModelPassword
    private lateinit var binding: FragmentContainerBinding
    private val viewSheetBin: SheeDialogBinding by lazy { SheeDialogBinding.inflate(layoutInflater) }
    val languages: Array<String> by lazy { resources.getStringArray(passy.prog.R.array.colori_spinner) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            binding= FragmentContainerBinding.bind(view)
        fab()
        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun OpenShowSheetButon(entityPassword: EntityPassword) {
                showSheet()
            }




        })

        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        binding = FragmentContainerBinding.bind(view)
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        ViewModelProvider(this)[ViewModelPassword::class.java]
        viewModel.lista.observe(requireActivity()) {
            adapter.submitList(it)
        }

        hideFabs()

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

    private fun showSheet() {
        dependencesHilt.hideStatusBAr()

        MaterialDialog(requireContext(), BottomSheet()).show {
            cornerRadius(18f)
            customView(view = viewSheetBin.root, scrollable = true)
            title(passy.prog.R.string.insertitle)

            viewSheetBin.passwordInput.setOnClickListener {
            }
            with(viewSheetBin.spinner) {
                this.prompt = getString(passy.prog.R.string.app_name)
                onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            Toast.makeText(
                                requireContext(),
                                languages[position],
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
            }


        }
        with(viewSheetBin.ivAvatarSheet) {
            val radius = resources.getDimension(passy.prog.R.dimen.md_dialog_default_corner_radius)
            this.shapeAppearanceModel = this.shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build()
        }
    }

    private fun exeptionCorotinesHandler(): CoroutineExceptionHandler {
        val corotineExeption = CoroutineExceptionHandler { _, Throwable ->
            run {
                Log.d("Frag", Throwable.message.toString())
            }
        }
        return corotineExeption;
    }

    private fun fab() {

        binding.fbFrag.setOnClickListener {
        showSheet()

        }
    }

}


