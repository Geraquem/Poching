package com.mmfsin.pochounter.domain.usecases

import android.content.Context
import com.mmfsin.pochounter.R
import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Points
import com.mmfsin.pochounter.domain.models.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IRoomsRepository
) {
    suspend fun execute(roomName: String, points: Points, players: List<String>): String {
        val newId = UUID.randomUUID().toString()

        val playersList = players.map { playerName ->
            Player(
                id = UUID.randomUUID().toString(),
                roomId = newId,
                name = playerName.ifBlank { context.getString(R.string.player_default) },
                points = 0
            )
        }

        val formatter = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())
        val creation = formatter.format(Date())

        val room = Room(
            id = newId,
            name = roomName,
            totalPlayers = players.size,
            points = points,
            players = playersList,
            creation = creation
        )

        repository.createRoom(room)
        return newId
    }
}