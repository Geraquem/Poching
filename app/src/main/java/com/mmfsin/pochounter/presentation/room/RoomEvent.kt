package com.mmfsin.pochounter.presentation.room

import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room

sealed class RoomEvent {
    data class GetRoom(val room: Room) : RoomEvent()
    data class NewPlayerAdded(val player: Player) : RoomEvent()
    data class UpdatedPlayerName(val newName: String, val position: Int) : RoomEvent()
    data class RestartedPlayerPoints(val position: Int) : RoomEvent()
    data class PlayerDeleted(val position: Int) : RoomEvent()
    data object UpdateResult : RoomEvent()
    data object SWW : RoomEvent()
}