package com.mmfsin.pochounter.presentation.menu

import com.mmfsin.pochounter.domain.models.Room

sealed class MenuEvent {
    data class GetRooms(val rooms: List<Room>) : MenuEvent()
    data class RoomCreated(val room: Room) : MenuEvent()
    data object SWW : MenuEvent()
}