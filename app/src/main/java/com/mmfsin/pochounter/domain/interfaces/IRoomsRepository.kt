package com.mmfsin.pochounter.domain.interfaces

import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room

interface IRoomsRepository {
    suspend fun getRooms(): List<Room>
    suspend fun createRoom(room: Room)
    suspend fun deleteRoom(roomId: String)
    suspend fun getRoomData(roomId: String): Room?

    suspend fun addNewPlayer(roomId: String, playerName: String): Player
    suspend fun deletePlayer(playerId: String)
    suspend fun updatePlayerPoints(playerId: String, points: Int)
    suspend fun updatePlayerName(playerId: String, newName: String)
}