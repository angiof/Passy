package passy.prog.views

import BtnSheetEdit
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    val fragment: FragError = FragError()
    val ctx: FragmentContainer get() = this@FragmentContainer


    //curoines
    private val coroutineScopeInsert: CoroutineScope by lazy { CoroutineScope(Dispatchers.Default) }
    private lateinit var viewModel: ViewModelPassword
    private lateinit var binding: passy.prog.databinding.FragmentContainerBinding
    var colors: String? = null
    private val bindSheetLayout: SheeDialogBinding by lazy {
        SheeDialogBinding.inflate(
            layoutInflater
        )
    }
    private val bindSheetLayout2: SheeDialogBinding by lazy {
        SheeDialogBinding.inflate(
            layoutInflater
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val utils: utils = utils()
        utils.hideToolbarAndStatusBar(view.context)

        viewModel = ViewModelProvider(this)[ViewModelPassword::class.java]
        binding = FragmentContainerBinding.bind(view)


        val adapter = MyAdapter(object : MyAdapter.OnCardButtonsClick {
            override suspend fun OpenShowSheetButon(entityPassword: EntityPassword) {
                //showSheet()
            }

            @SuppressLint("SetTextI18n")
            override suspend fun onUpdatePassword(entityPassword: EntityPassword) {

                // dialogs(entityPassword)
                val sheet2 = BtnSheetEdit()

                val p: PersistentData = PersistentData()

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
    @SuppressLint("ResourceAsColor")
    private fun showSheet() {
        var ic_signature: String? = null

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
        binding.fbFrag.setOnClickListener {
            val sheet2: BTnSheetDialogFragment = BTnSheetDialogFragment()
            sheet2.show(requireActivity().supportFragmentManager, "sheet")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        fabInsert()
    }


    fun dialogs(entityPassword: EntityPassword): MaterialDialog {
        SheeDialogBinding.inflate(layoutInflater)
        val s = MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customView(view = bindSheetLayout2.root, scrollable = true)
            cornerRadius(res = R.dimen.md_dialog_default_corner_radius)

            bindSheetLayout2.txtLoghin.editText?.setText(entityPassword.loghin)
                .toString()

            bindSheetLayout2.txtPassword.setText(entityPassword.loghin).toString()

            bindSheetLayout2.btnSave.setOnClickListener {

                val k: String? = bindSheetLayout2.txtLoghin.editText?.text.toString()
                val p: String? = bindSheetLayout2.txtPassword.text?.toString()

                GlobalScope.launch {
                    viewModel.updatePassword(
                        EntityPassword(
                            entityPassword.id,
                            null,
                            k,
                            p,
                        )
                    )
                }

                dismiss()
            }

        }
        return s
    }



}