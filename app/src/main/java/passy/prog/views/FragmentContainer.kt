package passy.prog.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.compose.ui.Alignment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.databinding.SheeDialogBinding
import passy.prog.db.EntityPassword
import passy.prog.viewmodel.ViewModelPassword
import javax.inject.Inject

@AndroidEntryPoint
class FragmentContainer : Fragment(R.layout.fragment_container) {
    @Inject
    lateinit var dependencesHilt: DependencesHilt
    //curoines
    private val coroutineScopeInsert: CoroutineScope by lazy { CoroutineScope(Dispatchers.Default) }
    private lateinit var viewModel: ViewModelPassword
    private lateinit var binding: passy.prog.databinding.FragmentContainerBinding
    var colors: String? = null
    private val bindSheetLayout: passy.prog.databinding.SheeDialogBinding by lazy {
        SheeDialogBinding.inflate(
            layoutInflater
        )
    }
    val tipologia: Array<String> by lazy { resources.getStringArray(R.array.avatar_password) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]

        binding = FragmentContainerBinding.bind(view)

        fabInsert()

        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun OpenShowSheetButon(entityPassword: EntityPassword) {
                //showSheet()
            }

        })

        binding = FragmentContainerBinding.bind(view)
        binding.recyclerView.apply {
            val decorationSpan=DividerItemDecoration(requireContext(),LinearLayout.VERTICAL)
            addItemDecoration(decorationSpan)
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

    @SuppressLint("ResourceAsColor")
    private fun showSheet() {
        var ic_signature: String? = null
        dependencesHilt.hideStatusBAr()

        MaterialDialog(requireContext(), BottomSheet()).show {
            cornerRadius(18f)
            customView(view = bindSheetLayout.root, scrollable = true)
            title(passy.prog.R.string.insertitle)



            with(bindSheetLayout.spinner) {
                this.prompt = getString(R.string.app_name)
                onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            ic_signature = tipologia[position]
                            when (position) {

                                0 -> {
                                    bindSheetLayout.ivAvatarSheet.visibility = View.VISIBLE
                                    bindSheetLayout.ivAvatarSheet.setBackgroundResource(R.drawable.ic_jobss)
                                }
                                1 -> {
                                    bindSheetLayout.ivAvatarSheet.setBackgroundResource(R.drawable.ic_baseline_error_outline_24_viola)
                                    bindSheetLayout.ivAvatarSheet.visibility = View.VISIBLE
                                }
                                2 -> {
                                    bindSheetLayout.ivAvatarSheet.setBackgroundResource(R.drawable.ic_baseline_videogame_asset_24)
                                    bindSheetLayout.ivAvatarSheet.visibility = View.VISIBLE
                                }
                                3 -> {
                                    bindSheetLayout.ivAvatarSheet.setBackgroundResource(R.drawable.ic_baseline_error_outline_24)
                                    bindSheetLayout.ivAvatarSheet.visibility = View.VISIBLE
                                }
                                4 -> {
                                    bindSheetLayout.ivAvatarSheet.setBackgroundResource(R.drawable.ic_baseline_email_24)
                                    bindSheetLayout.ivAvatarSheet.visibility = View.VISIBLE
                                }
                                else -> bindSheetLayout.ivAvatarSheet.visibility = View.GONE
                            }


                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }

                bindSheetLayout.ivGreen.setOnClickListener {
                    colors="g"
                }
                bindSheetLayout.ivRed.setOnClickListener {
                    colors="r"
                }



                bindSheetLayout.btnSave.setOnClickListener {

                    val user: String = bindSheetLayout.txtUser.text.toString()
                    val password: String = bindSheetLayout.txtPassword.text.toString()

                    runBlocking {
                        viewModel.insertPasswordViewModel(
                            EntityPassword(
                                0,
                                user,
                                password,
                                colors,
                                ic_signature
                            )
                        )
                    }
                }
            }
        }
    }

    private fun fabInsert() {

        binding.fbFrag.setOnClickListener {
            showSheet()

        }
    }

}


