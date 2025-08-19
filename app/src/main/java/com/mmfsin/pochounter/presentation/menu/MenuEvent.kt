package com.mmfsin.pochounter.presentation.menu

sealed class MenuEvent {
    data class RoomCreated(val roomName: String) : MenuEvent()
    data object SWW : MenuEvent()
}