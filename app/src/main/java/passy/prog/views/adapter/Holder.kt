package passy.prog.views.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import passy.prog.R
import passy.prog.databinding.LyListaItemsBinding
import passy.prog.db.EntityPassword

class PasswordViewHolder(
    private val binding: LyListaItemsBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    fun binder(entityPassword: EntityPassword) {
        binding.apply {
            this.labelLoghin.setText(entityPassword.loghin)
            this.labelPassword.setText(entityPassword.password)


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
                Toast.makeText(card.context, "biometrico", Toast.LENGTH_SHORT).show()
                if (this.labelPassword.visibility ==View.VISIBLE){
                    this.labelPassword.visibility = View.GONE
                }else if (binding.labelPassword.visibility == View.GONE){
                    this.labelPassword.visibility = View.VISIBLE
                }
                this.labelPassword.setOnClickListener {
                    it.visibility = View.GONE
                }
            }
        }
    }
}
