package com.mmfsin.pochounter.domain.usecases

import android.content.Context
import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Points
import com.mmfsin.pochounter.domain.models.Room
import com.mmfsin.pochounter.utils.getRandomFunnyWord
import com.mmfsin.pochounter.utils.toSpanishDate
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    @ApplicationContext val context: Context, private val repository: IRoomsRepository
) {
    suspend fun execute(roomName: String, points: Points, players: List<String>): String {
        val newId = UUID.randomUUID().toString()

        val playersList = players.map { playerName ->
            Player(
                id = UUID.randomUUID().toString(),
                roomId = newId,
                name = playerName.ifBlank { getRandomFunnyWord() },
                points = 0
            )
        }

        val room = Room(
            id = newId,
            name = roomName.ifBlank { getRandomFunnyWord() },
            totalPlayers = players.size,
            points = points,
            players = playersList,
            creation = Date().toSpanishDate()
        )

        repository.createRoom(room)
        return newId
    }
}