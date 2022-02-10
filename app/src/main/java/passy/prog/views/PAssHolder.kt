package passy.prog.views

import androidx.recyclerview.widget.RecyclerView
import passy.prog.databinding.LyListaItemsBinding
import passy.prog.db.EntityPassword

class PAssHolder(listaItemsBinding: LyListaItemsBinding) :
    RecyclerView.ViewHolder(listaItemsBinding.root) {

        val login=listaItemsBinding.labelLoghin
        fun render(password: EntityPassword){
        login.setText(password.loghin)

        }

}