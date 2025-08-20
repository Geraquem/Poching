package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import javax.inject.Inject

class DeletePlayerUseCase @Inject constructor(private val repository: IRoomsRepository) {
    suspend fun execute(playerId: String) = repository.deletePlayer(playerId)
}