package com.mmfsin.pochounter.presentation.room

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.pochounter.base.BaseFragment
import com.mmfsin.pochounter.databinding.FragmentRoomBinding
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.room.adapter.PlayersAdapter
import com.mmfsin.pochounter.utils.ROOM_ID
import com.mmfsin.pochounter.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : BaseFragment<FragmentRoomBinding, RoomViewModel>() {

    override val viewModel: RoomViewModel by viewModels()
    private lateinit var mContext: Context

    private var roomId: String? = null
    private var players: MutableList<Player> = mutableListOf()

    private var playersAdapter: PlayersAdapter? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentRoomBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        roomId = arguments?.getString(ROOM_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomId?.let { id -> viewModel.getRoomData(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply {}
    }

    override fun setListeners() {
        binding.apply {
            btnAddPlayer.setOnClickListener {
                roomId?.let { id -> viewModel.addNewPlayer(id) }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoomEvent.GetRoom -> setUpRoomData(event.room)
                is RoomEvent.NewPlayerAdded -> addNewPlayer(event.player)
                is RoomEvent.SWW -> error()
            }
        }
    }

    private fun setUpRoomData(room: Room) {
        binding.apply {
            toolbar.apply {
                tvTitle.text = room.name
                ivBack.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
            }

            if (room.totalPlayers == 0) tvNoPlayers.isVisible = true
            else {
                players = room.players.toMutableList()
                rvPlayers.apply {
                    layoutManager = LinearLayoutManager(activity?.applicationContext)
                    playersAdapter = PlayersAdapter(players)
                    adapter = playersAdapter
                }
            }
        }
    }

    private fun addNewPlayer(player: Player) {
        playersAdapter?.addNewPlayer(player)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}