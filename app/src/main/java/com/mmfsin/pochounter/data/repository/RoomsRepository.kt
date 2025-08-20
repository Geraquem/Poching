package com.mmfsin.pochounter.data.repository

import android.content.Context
import com.mmfsin.pochounter.data.mappers.createPlayerDTO
import com.mmfsin.pochounter.data.mappers.createRoomDTO
import com.mmfsin.pochounter.data.mappers.toPlayer
import com.mmfsin.pochounter.data.mappers.toRoom
import com.mmfsin.pochounter.data.models.PlayerDTO
import com.mmfsin.pochounter.data.models.RoomDTO
import com.mmfsin.pochounter.domain.interfaces.IRealmDatabase
import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.utils.ID
import com.mmfsin.pochounter.utils.ROOM_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.ext.query
import java.util.UUID
import javax.inject.Inject

class RoomsRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IRoomsRepository {

    override suspend fun getRooms(): List<Room> {
        val result = mutableListOf<Room>()
        val rooms = realmDatabase.getObjectsFromRealm { query<RoomDTO>().find() }
        rooms.forEach { room ->
            val players = getPlayers(room.id)
            result.add(room.toRoom(players))
        }
        return result
    }

    override suspend fun createRoom(room: Room) {
        room.players.forEach { player ->
            realmDatabase.addObject { createPlayerDTO(player) }
        }

        val roomDTO = createRoomDTO(room)
        realmDatabase.addObject { roomDTO }
    }

    override suspend fun deleteRoom(roomId: String) {
        realmDatabase.deleteObject(RoomDTO::class, ID, roomId)
        val players = getPlayers(roomId)
        players.forEach { player ->
            realmDatabase.deleteObject(PlayerDTO::class, ID, player.id)
        }
    }

    override suspend fun getRoomData(roomId: String): Room? {
        val room = realmDatabase.getObjectFromRealm(RoomDTO::class, ID, roomId)
        return room?.toRoom(getPlayers(roomId))
    }

    private fun getPlayers(roomId: String): List<PlayerDTO> {
        return realmDatabase.getObjectsFromRealm {
            query<PlayerDTO>("$ROOM_ID == $0", roomId).find()
        }
    }

    override suspend fun addNewPlayer(roomId: String): Player {
        val newPlayer = PlayerDTO().apply {
            id = UUID.randomUUID().toString()
            this.roomId = roomId
            name = "Player"
            points = 0
        }
        realmDatabase.addObject { newPlayer }
        return newPlayer.toPlayer()
    }

    override suspend fun updatePlayerPoints(playerId: String, points: Int) {
        realmDatabase.write {
            val player = query<PlayerDTO>("id == $0", playerId).first().find()
            player?.points = points
        }
    }
}