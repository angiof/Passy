package passy.prog.utils

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import com.google.android.material.textfield.TextInputEditText
import passy.prog.databinding.EditSheetBinding
import passy.prog.db.EntityPassword
import passy.prog.views.BtnSheetEdit
import passy.prog.views.FragmentContainer


fun FragmentContainer.sendDescriptionToSheet(entityPassword: EntityPassword) {
    val bundle = Bundle()
    bundle.putParcelable("0", entityPassword)
    // Passiamo il bundle al fragment successivo
    val fragmentIntermediario = BtnSheetEdit()
    fragmentIntermediario.arguments = bundle
    fragmentIntermediario.show(
        requireActivity().supportFragmentManager,
        "FragmentContainer"
    )
}


fun BtnSheetEdit.setFields(bindingFragSheet2: EditSheetBinding) {
    // Recuperiamo il bundle dai arguments del fragment
    val bundle = arguments
// Recuperiamo l'oggetto dal bundle
    val oggetto = bundle?.getParcelable<EntityPassword>("0") as EntityPassword
    bindingFragSheet2.desc.setText(oggetto.descrizione)
    bindingFragSheet2.txtUser.setText(oggetto.loghin)
    bindingFragSheet2.txtPassword.setText(oggetto.password)
}

fun BtnSheetEdit.setId(): Int {
    val bundle = arguments
    val oggetto = bundle?.getParcelable<EntityPassword>("0") as EntityPassword
    return oggetto.id
}

fun TextInputEditText.onEditor(TextInputEditTextInput: TextInputEditText) =
    TextInputEditTextInput.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // do something, e.g. set your TextView here via .setText()
            val imm: InputMethodManager =
                v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            return@OnEditorActionListener true
        }
        false
    })