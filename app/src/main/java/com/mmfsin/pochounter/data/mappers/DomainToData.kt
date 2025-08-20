package com.mmfsin.pochounter.data.mappers

import com.mmfsin.pochounter.data.models.PlayerDTO
import com.mmfsin.pochounter.data.models.RoomDTO
import com.mmfsin.pochounter.domain.models.Player
import com.mmfsin.pochounter.domain.models.Room

fun createRoomDTO(r: Room) = RoomDTO().apply {
    id = r.id
    name = r.name
    totalPlayers = r.totalPlayers
    pointsOkBase = r.points.pointsOkBase
    pointsOkExtra = r.points.pointsOkExtra
    pointsKo = r.points.pointsKo
    creation = r.creation
}

fun createPlayerDTO(p: Player) = PlayerDTO().apply {
    id = p.id
    name = p.name
    roomId = p.roomId
    points = p.points
}