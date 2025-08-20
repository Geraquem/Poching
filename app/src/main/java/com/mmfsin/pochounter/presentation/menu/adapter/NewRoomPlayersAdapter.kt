package com.mmfsin.pochounter.presentation.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.databinding.ItemAddPlayerBinding
import com.mmfsin.pochounter.presentation.menu.interfaces.INewPlayerListener

class NewRoomPlayersAdapter(
    private val players: MutableList<String>,
    private val listener: INewPlayerListener
) : RecyclerView.Adapter<NewRoomPlayersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAddPlayerBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(name: String, position: Int) {
            binding.apply {
                etName.setHint(name)
                if (position == 0 || position == 1) ivDeletePlayer.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_player, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player, position)
        holder.binding.ivDeletePlayer.setOnClickListener { listener.deletePlayer(position) }
    }

    override fun getItemCount(): Int = players.size

    fun addNewPlayer(name: String) {
        players.add(name)
        notifyItemInserted(players.size - 1)
    }

    fun deleteNewPlayer(position: Int) {
        try {
            players.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, players.size)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun getPlayers(): List<String> = players.toList()
}