package com.mmfsin.pochounter.presentation.room.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.databinding.ItemPlayerBinding
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Points
import com.mmfsin.pochounter.presentation.room.interfaces.IPlayersListener

class PlayersAdapter(
    private val players: MutableList<Player>,
    private val points: Points,
    private val listener: IPlayersListener
) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlayerBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(player: Player, points: Points) {
            binding.apply {
                tvName.text = player.name
                tvPoints.text = "${player.points}"

                tvPointsOkBase.text = "${points.pointsOkBase}"
                tvPointsOkExtra.text = "${points.pointsOkExtra}"
                tvPointsKo.text = "${points.pointsKo}"

                tvPointsOkBaseTwo.text = "${points.pointsOkBase * 2}"
                tvPointsOkExtraTwo.text = "${points.pointsOkExtra * 2}"
                tvPointsKoTwo.text = "${points.pointsKo * 2}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player, points)

        holder.binding.ivSettings.setOnClickListener {
            listener.playerSettings(player.id, player.name, position)
        }

        holder.binding.llPointsOkBase.setOnClickListener {
            player.points += points.pointsOkBase
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points, isError = false)
        }

        holder.binding.llPointsOkExtra.setOnClickListener {
            player.points += points.pointsOkExtra
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points, isError = false)
        }

        holder.binding.llPointsKo.setOnClickListener {
            player.points += points.pointsKo
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points, isError = true)
        }

        holder.binding.llPointsOkBaseTwo.setOnClickListener {
            player.points += points.pointsOkBase * 2
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points, isError = false)
        }

        holder.binding.llPointsOkExtraTwo.setOnClickListener {
            player.points += points.pointsOkExtra * 3
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points, isError = false)
        }

        holder.binding.llPointsKoTwo.setOnClickListener {
            player.points += points.pointsKo * 2
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points, isError = true)
        }
    }

    override fun getItemCount(): Int = players.size

    fun updatePlayerName(newName: String, position: Int) {
        players[position].name = newName
        notifyItemChanged(position)
    }

    fun restartPlayerPoints(position: Int) {
        players[position].points = 0
        notifyItemChanged(position)
    }

    fun addNewPlayer(player: Player) {
        players.add(player)
        notifyItemInserted(players.size - 1)
    }
}