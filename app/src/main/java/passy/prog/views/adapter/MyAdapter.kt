package passy.prog.views.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import passy.prog.R
import passy.prog.databinding.LyListaItemsBinding
import passy.prog.db.EntityPassword
import kotlin.system.measureTimeMillis

class MyAdapter(val onCardButtonsClick: OnCardButtonsClick) :
    ListAdapter<EntityPassword, MyAdapter.PasswordViewHolder>(DiffCallBack()) {
    var TAG: String = "MYADAPTER"
    val jobPadres: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main) }

    inner class PasswordViewHolder(private val binding: LyListaItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("ResourceAsColor")
        fun binder(entityPassword: EntityPassword) {
            binding.apply {
                binding.labelLoghin.setText(entityPassword.loghin)

                when (entityPassword.color) {
                    "n" -> binding.viewLayout.setBackgroundColor(R.color.purple_200)
                    "g" -> binding.viewLayout.setBackgroundColor(this.root.context.getColor(R.color.design_default_color_secondary_variant))
                    "r" -> binding.viewLayout.setBackgroundColor(this.root.context.getColor(R.color.redsoft))
                    null -> binding.viewLayout.setBackgroundColor(R.color.softGreen)
                }
                when (entityPassword.tipologia) {
                    "Lavoro" -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_jobs)

                    "Giochi" -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_jositick_inset)

                    "Importante" -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_imm)

                    "Uso_Personale" -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_prifile2)
                    "Posta" -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_email)

                    else -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_lock)


                }
                this.materialCardVIew.setOnClickListener { card ->

                    jobPadres.launch {
                        val timepo = measureTimeMillis {
                            //  onCardButtonsClick.OpenShowSheetButon(entityPassword)

                        }
                        Log.d(TAG, timepo.toString())
                    }
                }


            }
        }
    }

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
















