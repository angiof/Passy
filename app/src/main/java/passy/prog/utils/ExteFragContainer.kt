package passy.prog.utils

import android.os.Bundle
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


fun BtnSheetEdit.setFields(bindingFragSheet2 : EditSheetBinding) {
    // Recuperiamo il bundle dai arguments del fragment
    val bundle = arguments
// Recuperiamo l'oggetto dal bundle
    val oggetto = bundle?.getParcelable<EntityPassword>("0") as EntityPassword
    bindingFragSheet2.desc.setText(oggetto.descrizione)
    bindingFragSheet2.txtUser.setText(oggetto.loghin)
    bindingFragSheet2.txtPassword.setText(oggetto.password)
}
