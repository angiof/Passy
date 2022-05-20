package passy.prog.views.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import passy.prog.R
import passy.prog.databinding.LyListaItemsBinding
import passy.prog.db.EntityPassword


class MyAdapter(val onCardButtonsClick: OnCardButtonsClick, context: Context) :
    ListAdapter<EntityPassword, MyAdapter.PasswordViewHolder>(DiffCallBack()) {
    private var dataset: MutableList<EntityPassword> = mutableListOf()
    var TAG: String = "MYADAPTER"
    val builder = AlertDialog.Builder(context)
    val viewDialog: View = View.inflate(context, R.layout.custom_, null)
    val jobPadres: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main) }
    @OptIn(DelicateCoroutinesApi::class)
    inner class PasswordViewHolder(
        private val binding: LyListaItemsBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("ResourceAsColor")
        fun binder(entityPassword: EntityPassword) = runBlocking {
            binding.apply {
                this.labelLoghin.text = entityPassword.loghin
                this.labelPassword.text = entityPassword.password


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

                    else -> binding.ivAvatar.setBackgroundResource(R.drawable.ic_google_svgrepo_com)
                }
                if (entityPassword.loghin!!.contains("accenture", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_acure_icon)
                } else if (entityPassword.loghin.contains("microsoft", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_microsoft)
                } else if (entityPassword.loghin.contains("ig", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_instagram)
                } else if (entityPassword.loghin.contains("fb", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
                } else if (entityPassword.loghin.contains("git", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_github)
                } else if (entityPassword.loghin.contains("gitlab", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_gitlab)
                } else if (entityPassword.loghin.contains("apple", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_apple_brands)
                } else if (entityPassword.loghin.contains("android", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_android_brands)
                } else if (entityPassword.loghin.contains("paypal", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_paypal)
                } else if (entityPassword.loghin.contains("koltin", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_kotlin)
                } else if (entityPassword.loghin.contains("oracle", true)) {
                    binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_java)
                }

                this.btncopy.let { it ->
                    it.setOnClickListener {
                    it.context.copyToClipboard(this.labelPassword.text.toString(),it.context)
                    }
                }

                this.materialCardVIew.setOnClickListener { card ->
                    Toast.makeText(card.context, "biometrico", Toast.LENGTH_SHORT).show()
                    if (this.labelPassword.visibility == View.VISIBLE) {
                        this.labelPassword.visibility = View.GONE
                    } else if (binding.labelPassword.visibility == View.GONE) {
                        this.labelPassword.visibility = View.VISIBLE
                    }
                    this.labelPassword.setOnClickListener {
                        it.visibility = View.GONE
                    }
                }
                this.imageView.let {
                    it.setOnClickListener {
                        val builder = AlertDialog.Builder(it.context)
                        builder.setCancelable(true)
                        builder.setView(viewDialog)
                        val dialog = builder.create()
                        viewDialog.findViewById<TextView>(R.id.password_dialog).text = entityPassword.password
                        imagecontrol(entityPassword)
                        viewDialog.findViewById<TextView>(R.id.texttest).text =
                            entityPassword.loghin
                        viewDialog.findViewById<View>(R.id.edit).setOnClickListener {
                            viewDialog.parent
                            if (viewDialog.parent != null) {
                                (viewDialog.parent as ViewGroup).removeView(viewDialog) // <- fix
                            }
                            builder.setView(viewDialog)
                            GlobalScope.launch {
                                onCardButtonsClick.onUpdatePassword(entityPassword)
                            }
                            Log.d("dialog", "premuto edit ")
                            dialog.dismiss()
                        }

                        viewDialog.findViewById<View>(R.id.delate).setOnClickListener {
                            GlobalScope.launch {
                                onCardButtonsClick.onDelateCard(entityPassword)
                                dialog.dismiss()
                            }
                        }
                        viewDialog.findViewById<ImageView>(R.id.closeview).setOnClickListener {
                            dialog.dismiss()
                        }
                        // dialog.show()
                        if (viewDialog.parent != null) {
                            (viewDialog.parent as ViewGroup).removeView(viewDialog) // <- fix
                        }
                        dialog.show()

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
        suspend fun onDelateCard(entityPassword: EntityPassword)
        suspend fun OpenShowSheetButon(entityPassword: EntityPassword)
        suspend fun onUpdatePassword(entityPassword: EntityPassword)
    }

    fun run(position: Int) = runBlocking {
        launch(Dispatchers.IO) {
            onCardButtonsClick.onDelateCard(dataset[position])
        }
    }

    fun imagecontrol(entityPassword: EntityPassword) {
        val pipo = viewDialog.findViewById<ImageView>(R.id.avatardialog)
        if (entityPassword.loghin!!.contains("accenture", true)) {
            pipo.setBackgroundResource(R.drawable.ic_acure_icon)
        } else if (entityPassword.loghin.contains("microsoft", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_microsoft)
        } else if (entityPassword.loghin.contains("ig", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_instagram)
        } else if (entityPassword.loghin.contains("fb", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
        } else if (entityPassword.loghin.contains("git", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_github)
        } else if (entityPassword.loghin.contains("gitlab", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_gitlab)
        } else if (entityPassword.loghin.contains("apple", true)) {
            pipo.setBackgroundResource(R.drawable.ic_apple_brands)
        } else if (entityPassword.loghin.contains("android", true)) {
            pipo.setBackgroundResource(R.drawable.ic_android_brands)
        } else if (entityPassword.loghin.contains("paypal", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_paypal)
        } else if (entityPassword.loghin.contains("koltin", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_kotlin)
        } else if (entityPassword.loghin.contains("oracle", true)) {
            pipo.setBackgroundResource(R.drawable.ic_icons8_java)
        } else {
            pipo.setBackgroundResource(R.drawable.ic_prifile2)
        }
    }


    fun assetControl(context: Context,entityPassword: EntityPassword,binding: LyListaItemsBinding,viewDialog: View ){


        if (entityPassword.loghin!!.contains("accenture", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_acure_icon)
        } else if (entityPassword.loghin.contains("microsoft", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_microsoft)
        } else if (entityPassword.loghin.contains("ig", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_instagram)
        } else if (entityPassword.loghin.contains("fb", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
        } else if (entityPassword.loghin.contains("git", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_github)
        } else if (entityPassword.loghin.contains("gitlab", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_gitlab)
        } else if (entityPassword.loghin.contains("apple", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_apple_brands)
        } else if (entityPassword.loghin.contains("android", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_android_brands)
        } else if (entityPassword.loghin.contains("paypal", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_paypal)
        } else if (entityPassword.loghin.contains("koltin", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_kotlin)
        } else if (entityPassword.loghin.contains("oracle", true)) {
            binding.ivAvatar.setBackgroundResource(R.drawable.ic_icons8_java)
        }
    }


    @SuppressLint("ServiceCast")
    fun Context.copyToClipboard(text: CharSequence,context: Context){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        clipboard.setPrimaryClip(clip)
    }
}