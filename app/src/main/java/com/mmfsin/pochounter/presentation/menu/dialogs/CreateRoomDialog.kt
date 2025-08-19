package com.mmfsin.pochounter.presentation.menu.dialogs

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.base.BaseBottomSheet
import com.mmfsin.pochounter.databinding.DialogCreateRoomBinding
import com.mmfsin.pochounter.presentation.menu.adapter.NewRoomPlayersAdapter
import com.mmfsin.pochounter.presentation.menu.interfaces.INewPlayerListener

class CreateRoomDialog(
    private val create: (roomName: String) -> Unit
) : BaseBottomSheet<DialogCreateRoomBinding>(), INewPlayerListener {

    private var playersAdapter: NewRoomPlayersAdapter? = null

    override fun inflateView(inflater: LayoutInflater) = DialogCreateRoomBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = (resources.displayMetrics.heightPixels * 0.95).toInt()
            it.layoutParams = layoutParams
            behavior.peekHeight = layoutParams.height
        }
    }

    override fun setUI() {
        isCancelable = true
        setPlayers()
    }

    override fun setListeners() {
        binding.apply {
            llAddPlayer.setOnClickListener {
                playersAdapter?.addNewPlayer("Jugador ${playersAdapter?.itemCount?.plus(1)}")
                setPlayersCount()
            }

            btnAccept.setOnClickListener {
                create(etRoomName.text.toString())
                dismiss()
            }
        }
    }

    private fun setPlayers() {
        binding.apply {
            val initialPlayers = mutableListOf("Jugador 1", "Jugador 2")
            rvPlayers.apply {
                layoutManager = LinearLayoutManager(activity?.applicationContext)
                playersAdapter = NewRoomPlayersAdapter(initialPlayers, this@CreateRoomDialog)
                adapter = playersAdapter
            }
            setPlayersCount()
        }
    }

    override fun deletePlayer(position: Int) {
        playersAdapter?.deleteNewPlayer(position)
        setPlayersCount()
    }

    private fun setPlayersCount() {
        binding.apply {
            val count = "${playersAdapter?.itemCount}"
            val text = getString(R.string.room_players_title, count)
            binding.tvPlayers.text = text
        }
    }
}