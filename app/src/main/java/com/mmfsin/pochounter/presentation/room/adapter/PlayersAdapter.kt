package com.mmfsin.pochounter.presentation.room.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.databinding.ItemPlayerBinding
import com.mmfsin.pochounter.domain.models.Player

class PlayersAdapter(
    private val players: MutableList<Player>,
) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlayerBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(player: Player) {
            binding.apply {
                tvName.setText(player.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = players[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int = players.size

    fun addNewPlayer(player: Player) {
        players.add(player)
        notifyItemInserted(players.size - 1)
    }
}