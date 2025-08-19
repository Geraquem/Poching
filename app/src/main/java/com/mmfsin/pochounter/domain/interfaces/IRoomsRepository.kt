package com.mmfsin.pochounter.domain.interfaces

import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room

interface IRoomsRepository {
    suspend fun getRooms(): List<Room>
    suspend fun createRoom(roomName: String): String
    suspend fun getRoomData(roomId: String): Room?

    suspend fun addNewPlayer(roomId: String): Player
}