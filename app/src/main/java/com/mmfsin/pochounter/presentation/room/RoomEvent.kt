package com.mmfsin.pochounter.presentation.room

import com.mmfsin.pochounter.domain.models.Room

sealed class RoomEvent {
    data class GetRoom(val room: Room) : RoomEvent()
    data object SWW : RoomEvent()
}