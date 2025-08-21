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
import com.mmfsin.pochounter.base.bedrock.BedRockActivity
import com.mmfsin.pochounter.databinding.FragmentRoomBinding
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.room.adapter.PlayersAdapter
import com.mmfsin.pochounter.presentation.room.dialogs.AddPlayerDialog
import com.mmfsin.pochounter.presentation.room.dialogs.PlayerSettingsDialog
import com.mmfsin.pochounter.presentation.room.interfaces.IPlayersListener
import com.mmfsin.pochounter.utils.BEDROCK_STR_ARGS
import com.mmfsin.pochounter.utils.formatList
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
        roomId = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
//        roomId = arguments?.getString(ROOM_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomId?.let { id -> viewModel.getRoomData(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply {}
        getSounds()
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoomEvent.GetRoom -> setUpRoomData(event.room)
                is RoomEvent.NewPlayerAdded -> newPlayerAdded(event.player)
                is RoomEvent.UpdatedPlayerName -> playersAdapter?.updatePlayerName(
                    event.newName, event.position
                )

                is RoomEvent.RestartedPlayerPoints -> playersAdapter?.restartPlayerPoints(event.position)
                is RoomEvent.PlayerDeleted -> {
                    playersAdapter?.removePlayer(event.position)
                    checkPlayersCount()
                }

                is RoomEvent.UpdateResult -> updateResult()
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
            setUpToolbar(room.name)

            if (room.totalPlayers == 0) tvNoPlayers.isVisible = true
            players = room.players.toMutableList()
            rvPlayers.apply {
                layoutManager = LinearLayoutManager(activity?.applicationContext)
                playersAdapter = PlayersAdapter(players, room.points, this@RoomFragment)
                adapter = playersAdapter
            }
            updateResult()
        }
    }

    private fun setUpToolbar(roomName: String) {
        (activity as BedRockActivity).setUpToolbar(title = roomName, action = {
            activity?.showFragmentDialog(AddPlayerDialog { name ->
                roomId?.let { id -> viewModel.addNewPlayer(roomId = id, playerName = name) }
            })
        })
    }

    private fun newPlayerAdded(player: Player) {
        playersAdapter?.addNewPlayer(player)
        checkPlayersCount()
        if (playersAdapter?.itemCount == 1) roomId?.let { id -> viewModel.getRoomData(id) }
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
                restartPoints = { viewModel.resetPoints(playerId, position) },
                deletePlayer = { viewModel.deletePlayer(playerId, position) })
        )
    }

    private fun checkPlayersCount() {
        val v = if (playersAdapter?.itemCount == 0) View.VISIBLE else View.GONE
        binding.tvNoPlayers.visibility = v
        updateResult()
    }

    private fun updateResult() {
        val players = playersAdapter?.getPlayers()

        players?.let {
            val maxPoints = players.maxByOrNull { it.points }?.points
            val winners = players.filter { it.points == maxPoints }

            val text = if (winners.size == 1) winners.first().name
            else formatList(winners.map { it.name })

            binding.tvResult.text = getString(R.string.players_winner, text)

            playersAdapter?.updateWinner(winners.map { it.id })
        }
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