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
import android.view.Window
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
import passy.prog.utils.ARANCIA
import passy.prog.utils.ROSSO
import passy.prog.utils.UtilsFuns
import passy.prog.utils.VERDE


class MyAdapter(val onCardButtonsClick: OnCardButtonsClick, context: Context) :
    ListAdapter<EntityPassword, MyAdapter.PasswordViewHolder>(DiffCallBack()) {
    private var dataset: MutableList<EntityPassword> = mutableListOf()
    val viewDialog: View = View.inflate(context, R.layout.custom_, null)
    private var baseFuns: UtilsFuns = UtilsFuns()

    @OptIn(DelicateCoroutinesApi::class)
    inner class PasswordViewHolder(
        private val binding: LyListaItemsBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("ResourceAsColor")
        fun binder(entityPassword: EntityPassword) {
            binding.apply {
                this.labelLoghin.text = entityPassword.loghin
                this.labelPassword.text = entityPassword.password

                when (entityPassword.color) {
                    ARANCIA -> binding.viewLayout.setBackgroundColor(this.root.context.getColor(R.color.materialonrange))
                    VERDE-> binding.viewLayout.setBackgroundColor(this.root.context.getColor(R.color.softGreen2))
                    ROSSO -> binding.viewLayout.setBackgroundColor(this.root.context.getColor(R.color.redsoft2))
                    null -> binding.viewLayout.setBackgroundColor(R.color.softGreen)
                }

                baseFuns.AdapterFuns().setDefaultAvatar(binding.ivAvatar)

                baseFuns.AdapterFuns().setAssets(entityPassword = entityPassword, binding.ivAvatar)

                this.btncopy.let { it ->
                    it.setOnClickListener {
                        it.context.copyToClipboard(this.labelPassword.text.toString(), it.context)
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
                this.imOptions.let { it ->
                    it.setOnClickListener {
                        val builder = AlertDialog.Builder(it.context)
                        builder.setCancelable(true)
                        builder.setView(viewDialog)
                        val dialog = builder.create()
                        viewDialog.findViewById<TextView>(R.id.label_data).text =
                            entityPassword.data
                        if (entityPassword.descrizione.isNullOrEmpty()){
                        viewDialog.findViewById<TextView>(R.id.lb_descrizione).visibility = View.GONE
                        viewDialog.findViewById<TextView>(R.id.tv_descrizione).visibility = View.GONE
                        }else{
                        viewDialog.findViewById<TextView>(R.id.tv_descrizione).text =
                            entityPassword.descrizione
                        }
                        viewDialog.findViewById<TextView>(R.id.password_dialog).text =
                            entityPassword.password
                        setCustomDialogAssets(entityPassword)
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
                                object {
                                    val entityPassword = entityPassword
                                }
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
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
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

    fun setCustomDialogAssets(entityPassword: EntityPassword) {
        val asset = viewDialog.findViewById<ImageView>(R.id.avatardialog)
        if (entityPassword.loghin!!.contains("accenture", true)) {
            asset.setBackgroundResource(R.drawable.ic_acure_icon)
        } else if (entityPassword.loghin.contains("microsoft", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_microsoft)
        } else if (entityPassword.loghin.contains("ig", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_instagram)
        } else if (entityPassword.loghin.contains("fb", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_facebook_f__1_)
        } else if (entityPassword.loghin.contains("git", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_github)
        } else if (entityPassword.loghin.contains("gitlab", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_gitlab)
        } else if (entityPassword.loghin.contains("apple", true)) {
            asset.setBackgroundResource(R.drawable.ic_apple_brands)
        } else if (entityPassword.loghin.contains("android", true)) {
            asset.setBackgroundResource(R.drawable.ic_android_brands)
        } else if (entityPassword.loghin.contains("paypal", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_paypal)
        } else if (entityPassword.loghin.contains("koltin", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_kotlin)
        } else if (entityPassword.loghin.contains("oracle", true)) {
            asset.setBackgroundResource(R.drawable.ic_icons8_java)
        } else {
            asset.setBackgroundResource(R.drawable.ic_vpn)
        }
    }


    @SuppressLint("ServiceCast")
    fun Context.copyToClipboard(text: CharSequence, context: Context) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        clipboard.setPrimaryClip(clip)
    }


}