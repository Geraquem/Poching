package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    private val repository: IRoomsRepository
) {
    suspend fun execute(roomName: String) = repository.createRoom(roomName)
}