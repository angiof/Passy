package passy.prog.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import passy.prog.R
import passy.prog.databinding.FragmentContainerBinding
import passy.prog.databinding.SheeDialogBinding
import passy.prog.db.EntityPassword
import passy.prog.hilt.DependencesHilt
import passy.prog.viewmodel.BasePerformances
import passy.prog.viewmodel.ViewModelPassword
import passy.prog.views.adapter.MyAdapter
import javax.inject.Inject


@AndroidEntryPoint
class FragmentContainer : Fragment(R.layout.fragment_container) {
    val fragment: FragError = FragError()

    @Inject
    lateinit var dependencesHilt: DependencesHilt
    var basePerformances: BasePerformances = BasePerformances()

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]

        binding = FragmentContainerBinding.bind(view)


        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun OpenShowSheetButon(entityPassword: EntityPassword) {
                //showSheet()
            }

        })

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
        }

        hideFabs()
        fabInsert()

    }


    private fun hideFabs() {


        binding.fbFrag.setOnLongClickListener {
            if (binding.floatingActionButton.visibility == View.VISIBLE) {
                binding.floatingActionButton.visibility = View.GONE

                Toast.makeText(requireActivity(), "si", Toast.LENGTH_SHORT).show()
            } else if (binding.floatingActionButton.visibility == View.GONE) {
                binding.floatingActionButton.visibility = View.VISIBLE

            }
            return@setOnLongClickListener true
        }

        binding.fbFrag.setOnClickListener {
            viewModel.btnhide2(it.context)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private fun showSheet() {
        var ic_signature: String? = null
        dependencesHilt.hideStatusBAr()

        MaterialDialog(requireContext(), BottomSheet(LayoutMode.MATCH_PARENT)).show {
            cornerRadius(18f)

            customView(view = bindSheetLayout.root, scrollable = true)
            title(passy.prog.R.string.insertitle)


            bindSheetLayout.txtUser.setText("")
            bindSheetLayout.txtPassword.setText("")



            with(bindSheetLayout.spinner) {
                this.prompt = getString(R.string.app_name)




            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fabInsert() {
        binding.fbFrag.setOnClickListener { v ->
            val sheet2: BTnSheetDialogFragment = BTnSheetDialogFragment()
            sheet2.show(requireActivity().supportFragmentManager, "sheet")

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        fabInsert()
    }



}


