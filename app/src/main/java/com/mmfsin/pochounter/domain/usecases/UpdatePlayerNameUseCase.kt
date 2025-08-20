package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import javax.inject.Inject

class UpdatePlayerNameUseCase @Inject constructor(private val repository: IRoomsRepository) {
    suspend fun execute(playerId: String, newName: String) =
        repository.updatePlayerName(playerId, newName)
}