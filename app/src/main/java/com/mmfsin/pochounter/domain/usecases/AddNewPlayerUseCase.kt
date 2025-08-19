package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import com.mmfsin.pochounter.domain.models.Player
import javax.inject.Inject

class AddNewPlayerUseCase @Inject constructor(private val repository: IRoomsRepository) {
    suspend fun execute(roomId: String): Player = repository.addNewPlayer(roomId)
}