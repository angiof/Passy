package passy.prog.views.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import passy.prog.databinding.LyListaItemsBinding
import passy.prog.db.EntityPassword

class MyAdapter(val onCardButtonsClick: OnCardButtonsClick) :
    ListAdapter<EntityPassword, PasswordViewHolder>(DiffCallBack()) {
    var TAG: String = "MYADAPTER"
    val jobPadres: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding =
            LyListaItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binder(currentItem)
    }

    class DiffCallBack : DiffUtil.ItemCallback<EntityPassword>() {
        override fun areItemsTheSame(oldItem: EntityPassword, newItem: EntityPassword) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EntityPassword, newItem: EntityPassword) =
            oldItem == newItem
    }

    interface OnCardButtonsClick {
        //   suspend fun onDelateCard(entityPassword: EntityPassword)
        suspend fun OpenShowSheetButon(entityPassword: EntityPassword)
    }
}