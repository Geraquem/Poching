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
import com.mmfsin.pochounter.utils.formatList

class RoomsAdapter(
    private val rooms: MutableList<Room>,
    private val listener: IRoomListener
) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRoomBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(room: Room) {
            binding.apply {
                tvName.text = room.name

                if (room.totalPlayers == 0) tvPlayers.text = c.getString(R.string.no_players)
                else tvPlayers.text = formatList(room.players.map { it.name })

                tvDate.text = c.getString(R.string.room_creation_date, room.creation)
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
        holder.itemView.rootView.setOnLongClickListener {
            listener.longClickRoom(room.id, position)
            true
        }
    }

    override fun getItemCount(): Int = rooms.size

    fun addItem(item: Room, position: Int = rooms.size) {
        if (position in 0..rooms.size) {
            rooms.add(position, item)
            notifyItemInserted(position)
            notifyItemRangeChanged(position, rooms.size)
        }
    }

    fun removeItem(position: Int) {
        if (position in rooms.indices) {
            rooms.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, rooms.size)
        }
    }
}