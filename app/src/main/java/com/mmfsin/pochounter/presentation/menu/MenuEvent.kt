package com.mmfsin.pochounter.presentation.menu

sealed class MenuEvent {
    data class RoomCreated(val roomId: String) : MenuEvent()
    data class JoinedRoom(val roomId: String) : MenuEvent()
    data object SWW : MenuEvent()
}