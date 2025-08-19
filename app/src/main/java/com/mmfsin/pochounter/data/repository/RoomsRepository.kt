package com.mmfsin.pochounter.data.repository

import android.content.Context
import com.mmfsin.pochounter.data.mappers.toRoom
import com.mmfsin.pochounter.data.mappers.toRoomList
import com.mmfsin.pochounter.data.models.RoomDTO
import com.mmfsin.pochounter.domain.interfaces.IRealmDatabase
import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.utils.ID
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.ext.query
import java.util.UUID
import javax.inject.Inject

class RoomsRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IRoomsRepository {

    override suspend fun getRooms(): List<Room> {
        val rooms = realmDatabase.getObjectsFromRealm { query<RoomDTO>().find() }
        return rooms.toRoomList()
    }

    override suspend fun createRoom(roomName: String): String {
        val room = RoomDTO().apply {
            id = UUID.randomUUID().toString()
            name = roomName
            totalPlayers = 0
        }

        realmDatabase.addObject { room }
        return room.id
    }

    override suspend fun getRoomData(roomId: String): Room? {
        val room = realmDatabase.getObjectFromRealm(RoomDTO::class, ID, roomId)
        return room?.toRoom()
    }
}