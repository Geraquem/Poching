package com.mmfsin.pochounter.data.mappers

import com.mmfsin.pochounter.data.models.RoomDTO
import com.mmfsin.pochounter.domain.models.Room

fun RoomDTO.toRoom() = Room(id = id, name = name, totalPlayers = totalPlayers)

fun List<RoomDTO>.toRoomList() = this.map { it.toRoom() }