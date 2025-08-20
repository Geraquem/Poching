package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import javax.inject.Inject

class DeleteRoomUseCase @Inject constructor(private val repository: IRoomsRepository) {
    suspend fun execute(roomId: String) = repository.deleteRoom(roomId)
}