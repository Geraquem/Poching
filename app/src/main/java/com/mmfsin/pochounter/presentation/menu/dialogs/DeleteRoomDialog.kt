package com.mmfsin.pochounter.presentation.menu.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.pochounter.base.BaseDialog
import com.mmfsin.pochounter.databinding.DialogDeleteRoomBinding
import com.mmfsin.pochounter.presentation.menu.adapter.NewRoomPlayersAdapter

class DeleteRoomDialog(
    private val delete: () -> Unit
) : BaseDialog<DialogDeleteRoomBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogDeleteRoomBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                delete()
                dismiss()
            }
        }
    }
}