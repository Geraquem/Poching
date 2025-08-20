package com.mmfsin.pochounter.presentation.menu

import com.mmfsin.pochounter.domain.models.Room

sealed class MenuEvent {
    data class GetRooms(val rooms: List<Room>) : MenuEvent()
    data class RoomCreated(val roomId: String) : MenuEvent()
    data class RoomDeleted(val position: Int) : MenuEvent()
    data object SWW : MenuEvent()
}