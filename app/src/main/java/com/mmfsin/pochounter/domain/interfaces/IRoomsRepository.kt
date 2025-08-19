package com.mmfsin.pochounter.domain.interfaces

import com.mmfsin.pochounter.domain.models.Room

interface IRoomsRepository {
    suspend fun getRooms(): List<Room>
    suspend fun createRoom(roomName: String): String
    suspend fun getRoomData(roomId: String): Room?
}