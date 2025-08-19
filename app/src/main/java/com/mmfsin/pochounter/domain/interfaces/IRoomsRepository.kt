package com.mmfsin.pochounter.domain.interfaces

interface IRoomsRepository {
    suspend fun createRoom(roomName: String)
}