package com.mmfsin.pochounter.presentation.room.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.base.BaseDialog
import com.mmfsin.pochounter.databinding.DialogAddPlayerBinding

class AddPlayerDialog(private val accept: (name: String) -> Unit) :
    BaseDialog<DialogAddPlayerBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogAddPlayerBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
        binding.etName.setHint(getString(R.string.players_new_name_hint))
    }

    override fun setListeners() {
        binding.apply {
            btnAdd.setOnClickListener {
                accept(etName.text.toString())
                dismiss()
            }
        }
    }
}