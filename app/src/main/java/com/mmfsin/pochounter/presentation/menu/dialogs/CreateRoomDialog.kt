package com.mmfsin.pochounter.presentation.menu.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.pochounter.base.BaseDialog
import com.mmfsin.pochounter.databinding.DialogCreateRoomBinding

class CreateRoomDialog(
    private val create: (roomName: String) -> Unit
) : BaseDialog<DialogCreateRoomBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogCreateRoomBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
        binding.apply {
            btnAccept.text = "CREAR SALA"
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                create(etRoomName.text.toString())
                dismiss()
            }
        }
    }
}