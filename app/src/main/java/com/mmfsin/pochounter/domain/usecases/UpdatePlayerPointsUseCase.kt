package com.mmfsin.pochounter.domain.usecases

import com.mmfsin.pochounter.domain.interfaces.IRoomsRepository
import javax.inject.Inject

class UpdatePlayerPointsUseCase @Inject constructor(private val repository: IRoomsRepository) {
    suspend fun execute(playerId: String, points: Int) =
        repository.updatePlayerPoints(playerId, points)
}