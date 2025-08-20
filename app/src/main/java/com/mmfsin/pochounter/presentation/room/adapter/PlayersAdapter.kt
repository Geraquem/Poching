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

        holder.binding.llPointsOkBase.setOnClickListener {
            player.points += points.pointsOkBase
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points)
        }

        holder.binding.llPointsOkExtra.setOnClickListener {
            player.points += points.pointsOkExtra
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points)
        }

        holder.binding.llPointsKo.setOnClickListener {
            player.points += points.pointsKo
            notifyItemChanged(position)
            listener.updatePoints(player.id, player.points)
        }
    }

    override fun getItemCount(): Int = players.size

    fun addNewPlayer(player: Player) {
        players.add(player)
        notifyItemInserted(players.size - 1)
    }
}