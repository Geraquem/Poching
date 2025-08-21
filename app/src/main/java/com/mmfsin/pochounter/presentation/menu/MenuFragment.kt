package com.mmfsin.pochounter.presentation.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.base.BaseFragment
import com.mmfsin.pochounter.databinding.FragmentMenuBinding
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.presentation.main.MainActivity
import com.mmfsin.pochounter.presentation.menu.adapter.RoomsAdapter
import com.mmfsin.pochounter.presentation.menu.dialogs.CreateRoomDialog
import com.mmfsin.pochounter.presentation.menu.dialogs.DeleteRoomDialog
import com.mmfsin.pochounter.presentation.menu.interfaces.IRoomListener
import com.mmfsin.pochounter.utils.animateY
import com.mmfsin.pochounter.utils.countDown
import com.mmfsin.pochounter.utils.showAlpha
import com.mmfsin.pochounter.utils.showErrorDialog
import com.mmfsin.pochounter.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IRoomListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    private var roomsAdapter: RoomsAdapter? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onResume() {
        super.onResume()
        viewModel.getRooms()
    }

    override fun setUI() {
        binding.apply {
            llTitle.animateY(-500f, 10) { llTitle.isVisible = true }
            clBottom.animateY(500f, 10) { clBottom.isVisible = true }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnCreateRoom.setOnClickListener {
                activity?.showFragmentDialog(CreateRoomDialog { name, points, players ->
                    viewModel.createRoom(name, points, players)
                })
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.GetRooms -> setUpRooms(event.rooms)
                is MenuEvent.RoomCreated -> {
                    viewModel.getRooms()
                    navigateToRoom(event.roomId)
                }

                is MenuEvent.RoomDeleted -> {
                    roomsAdapter?.removeItem(event.position)
                    checkRoomsCount()
                }

                is MenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpRooms(rooms: List<Room>) {
        binding.apply {
            rvRooms.apply {
                layoutManager = LinearLayoutManager(activity?.applicationContext)
                roomsAdapter = RoomsAdapter(rooms.toMutableList(), this@MenuFragment)
                adapter = roomsAdapter
            }

            countDown(500) {
                llTitle.animateY(0f, 500)
                clBottom.animateY(0f, 500) {
                    rvRooms.showAlpha(true, 500)
                    checkRoomsCount()
                }
            }
        }
    }

    override fun clickRoom(roomId: String) = navigateToRoom(roomId)

    override fun longClickRoom(roomId: String, position: Int) {
        activity?.showFragmentDialog(DeleteRoomDialog {
            viewModel.deleteRoom(roomId, position)
        })
    }

    private fun navigateToRoom(roomId: String) {
        (activity as MainActivity).openBedRockActivity(
            navGraph = R.navigation.nav_graph_room, strArgs = roomId
        )
    }

    private fun checkRoomsCount() {
        val v = if (roomsAdapter?.itemCount == 0) View.VISIBLE else View.GONE
        binding.tvNoRooms.visibility = v
    }

    private fun error() = activity?.showErrorDialog(goBack = true)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}