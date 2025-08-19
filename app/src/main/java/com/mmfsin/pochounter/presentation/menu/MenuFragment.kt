package com.mmfsin.pochounter.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.pochounter.base.BaseFragment
import com.mmfsin.pochounter.databinding.FragmentMenuBinding
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.menu.adapter.RoomsAdapter
import com.mmfsin.pochounter.presentation.menu.dialogs.CreateRoomDialog
import com.mmfsin.pochounter.presentation.menu.interfaces.IRoomListener
import com.mmfsin.pochounter.presentation.utils.showErrorDialog
import com.mmfsin.pochounter.presentation.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IRoomListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    private var roomsAdapter: RoomsAdapter? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRooms()
    }

    override fun setUI() {
        binding.apply {}
    }

    override fun setListeners() {
        binding.apply {
            btnCreateRoom.setOnClickListener {
                activity?.showFragmentDialog(CreateRoomDialog { viewModel.createRoom(it) })
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.GetRooms -> setUpRooms(event.rooms)
                is MenuEvent.RoomCreated -> navigateToRoom(event.room)
                is MenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpRooms(rooms: List<Room>) {
        binding.apply {
            if (roomsAdapter == null) {
                rvRooms.apply {
                    layoutManager = LinearLayoutManager(activity?.applicationContext)
                    roomsAdapter = RoomsAdapter(rooms, this@MenuFragment)
                    adapter = roomsAdapter
                }
            } else {
                roomsAdapter?.notifyItemInserted(rooms.size - 1)
            }
        }
    }

    override fun clickRoom(room: Room) {
        navigateToRoom(room)
    }

    private fun navigateToRoom(room: Room) {
        Toast.makeText(mContext, room.id, Toast.LENGTH_SHORT).show()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}