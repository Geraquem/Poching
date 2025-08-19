package com.mmfsin.pochounter.presentation.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.pochounter.base.BaseFragment
import com.mmfsin.pochounter.databinding.FragmentMenuBinding
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.menu.MenuFragmentDirections.Companion.actionMenuFragmentToRoomFragment
import com.mmfsin.pochounter.presentation.menu.adapter.RoomsAdapter
import com.mmfsin.pochounter.presentation.menu.dialogs.CreateRoomDialog
import com.mmfsin.pochounter.presentation.menu.interfaces.IRoomListener
import com.mmfsin.pochounter.utils.showErrorDialog
import com.mmfsin.pochounter.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IRoomListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onResume() {
        super.onResume()
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
                is MenuEvent.RoomCreated -> navigateToRoom(event.roomId)
                is MenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpRooms(rooms: List<Room>) {
        binding.apply {
            rvRooms.apply {
                layoutManager = LinearLayoutManager(activity?.applicationContext)
                adapter = RoomsAdapter(rooms, this@MenuFragment)
            }
        }
    }

    override fun clickRoom(roomId: String) = navigateToRoom(roomId)

    private fun navigateToRoom(roomId: String) {
        findNavController().navigate(actionMenuFragmentToRoomFragment(roomId))
    }

    private fun error() = activity?.showErrorDialog(goBack = true)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}