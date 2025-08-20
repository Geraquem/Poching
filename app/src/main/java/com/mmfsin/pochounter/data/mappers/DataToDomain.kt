package com.mmfsin.pochounter.data.mappers

import com.mmfsin.pochounter.data.models.PlayerDTO
import com.mmfsin.pochounter.data.models.RoomDTO
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Points
import com.mmfsin.pochounter.domain.models.Room

fun PlayerDTO.toPlayer() = Player(
    id = id,
    roomId = roomId,
    name = name,
    points = points
)

fun List<PlayerDTO>.toPlayerList() = this.map { it.toPlayer() }

fun RoomDTO.toRoom(players: List<PlayerDTO>) = Room(
    id = id,
    name = name,
    totalPlayers = players.size,
    players = players.toPlayerList(),
    points = Points(pointsOkBase, pointsOkExtra, pointsKo),
    creation = creation
)

fun List<RoomDTO>.toRoomList(players: List<PlayerDTO>) = this.map { it.toRoom(players) }

fun createRoomDTO(r: Room) = RoomDTO().apply {
    id = r.id
    name = r.name
    totalPlayers = r.totalPlayers
    pointsOkBase = r.points.pointsOkBase
    pointsOkExtra = r.points.pointsOkExtra
    pointsKo = r.points.pointsKo
    creation = r.creation
}