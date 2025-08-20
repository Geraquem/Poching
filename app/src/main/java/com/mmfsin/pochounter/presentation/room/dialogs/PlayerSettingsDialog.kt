package com.mmfsin.pochounter.presentation.room.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.pochounter.base.BaseDialog
import com.mmfsin.pochounter.databinding.DialogPlayerSettingsBinding

class PlayerSettingsDialog(
    private val playerName: String,
    private val editName: (newName: String) -> Unit,
    private val restartPoints: () -> Unit,
    private val deletePlayer: () -> Unit
) : BaseDialog<DialogPlayerSettingsBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogPlayerSettingsBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
        binding.etName.setText(playerName)
    }

    override fun setListeners() {
        binding.apply {
            btnEditName.setOnClickListener {
                editName(etName.text.toString())
                dismiss()
            }

            btnResetPoints.setOnClickListener {
                restartPoints()
                dismiss()
            }

            btnDelete.setOnClickListener {
                deletePlayer()
                dismiss()
            }
        }
    }
}