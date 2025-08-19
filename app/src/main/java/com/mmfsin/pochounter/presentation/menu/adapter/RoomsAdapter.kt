package com.mmfsin.pochounter.presentation.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.databinding.ItemRoomBinding
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.menu.interfaces.IRoomListener

class RoomsAdapter(
    private val rooms: List<Room>,
    private val listener: IRoomListener
) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRoomBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(room: Room) {
            binding.apply {
                tvName.text = room.name

                val players =
                    if (room.totalPlayers == 0) R.string.no_players else R.string.error_btn
                tvPlayers.text = c.getString(players)

                itemView.rootView
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room)
        holder.itemView.rootView.setOnClickListener { listener.clickRoom(room.id) }
    }

    override fun getItemCount(): Int = rooms.size
}