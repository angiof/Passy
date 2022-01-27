package passy.prog.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import passy.prog.databinding.LyListaItemsBinding
import passy.prog.db.EntityPassword

class MyAdapter(val onCardButtonsClick: OnCardButtonsClick) :
    ListAdapter<EntityPassword, MyAdapter.PasswordViewHolder>(DiffCallBack()) {
    private var dataSet: MutableList<EntityPassword> = mutableListOf()

    inner class PasswordViewHolder(private val binding: LyListaItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(entityPassword: EntityPassword) {
            binding.apply {
                binding.labelLoghin.setText(entityPassword.loghin)
            }
            binding.btnCard.setOnClickListener {
                GlobalScope.launch {
                    onCardButtonsClick.onDelateCard(entityPassword)

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding =
            LyListaItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordViewHolder(binding)
    }

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
        suspend fun onDelateCard(entityPassword: EntityPassword)
    }

}
















