package com.mmfsin.pochounter.presentation.room

import android.content.Context
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.base.BaseFragment
import com.mmfsin.pochounter.databinding.FragmentRoomBinding
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.room.adapter.PlayersAdapter
import com.mmfsin.pochounter.presentation.room.dialogs.PlayerSettingsDialog
import com.mmfsin.pochounter.presentation.room.interfaces.IPlayersListener
import com.mmfsin.pochounter.utils.ROOM_ID
import com.mmfsin.pochounter.utils.showErrorDialog
import com.mmfsin.pochounter.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : BaseFragment<FragmentRoomBinding, RoomViewModel>(), IPlayersListener {

    override val viewModel: RoomViewModel by viewModels()
    private lateinit var mContext: Context

    private var roomId: String? = null
    private var players: MutableList<Player> = mutableListOf()

    private var playersAdapter: PlayersAdapter? = null

    private lateinit var soundPool: SoundPool
    private var soundClickOk: Int = 0
    private var soundClickKo: Int = 0

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
        getSounds()
    }

    override fun setListeners() {
        binding.apply {
            toolbar.ivAdd.setOnClickListener {
//                roomId?.let { id -> viewModel.addNewPlayer(id) }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoomEvent.GetRoom -> setUpRoomData(event.room)
                is RoomEvent.NewPlayerAdded -> addNewPlayer(event.player)
                is RoomEvent.UpdatedPlayerName -> playersAdapter?.updatePlayerName(
                    event.newName, event.position
                )

                is RoomEvent.RestartedPlayerPoints -> playersAdapter?.restartPlayerPoints(event.position)
                is RoomEvent.SWW -> error()
            }
        }
    }


    private fun getSounds() {
        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        soundClickOk = soundPool.load(requireContext(), R.raw.pop, 1)
        soundClickKo = soundPool.load(requireContext(), R.raw.error, 1)
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
                    playersAdapter = PlayersAdapter(players, room.points, this@RoomFragment)
                    adapter = playersAdapter
                }
            }
        }
    }

    private fun addNewPlayer(player: Player) {
//        playersAdapter?.addNewPlayer(player)
    }

    override fun updatePoints(playerId: String, points: Int, isError: Boolean) {
        val sound = if (isError) soundClickKo else soundClickOk
        soundPool.play(sound, 1f, 1f, 0, 0, 1f)
        viewModel.updatePoints(playerId, points)
    }

    override fun playerSettings(playerId: String, name: String, position: Int) {
        activity?.showFragmentDialog(
            PlayerSettingsDialog(playerName = name,
                editName = { newName -> viewModel.editPlayerName(playerId, newName, position) },
                restartPoints = { viewModel.resetPoints(playerId, position) })
        )
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}