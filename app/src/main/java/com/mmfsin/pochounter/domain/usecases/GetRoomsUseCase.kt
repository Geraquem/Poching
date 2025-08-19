package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import com.mmfsin.pochounter.domain.models.Room
import javax.inject.Inject

class GetRoomsUseCase @Inject constructor(private val repository: IRoomsRepository) {
    suspend fun execute(): List<Room> = repository.getRooms()
}