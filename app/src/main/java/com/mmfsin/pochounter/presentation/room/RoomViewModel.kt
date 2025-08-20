package com.mmfsin.pochounter.presentation.room

import com.mmfsin.pochounter.base.BaseViewModel
import com.mmfsin.pochounter.domain.usecases.AddNewPlayerUseCase
import com.mmfsin.pochounter.domain.usecases.GetRoomDataUseCase
import com.mmfsin.pochounter.domain.usecases.UpdatePlayerNameUseCase
import com.mmfsin.pochounter.domain.usecases.UpdatePlayerPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRoomDataUseCase: GetRoomDataUseCase,
    private val addNewPlayerUseCase: AddNewPlayerUseCase,
    private val updatePlayerNameUseCase: UpdatePlayerNameUseCase,
    private val updatePointsUseCase: UpdatePlayerPointsUseCase
) : BaseViewModel<RoomEvent>() {

    fun getRoomData(roomId: String) {
        executeUseCase(
            { getRoomDataUseCase.execute(roomId) },
            { result ->
                if (result != null) _event.value = RoomEvent.GetRoom(result)
                else _event.value = RoomEvent.SWW
            },
            { _event.value = RoomEvent.SWW }
        )
    }

    fun addNewPlayer(roomId: String) {
        executeUseCase(
            { addNewPlayerUseCase.execute(roomId) },
            { result -> _event.value = RoomEvent.NewPlayerAdded(result) },
            { _event.value = RoomEvent.SWW }
        )
    }

    fun updatePoints(playerId: String, points: Int) {
        executeUseCase(
            { updatePointsUseCase.execute(playerId, points) },
            { },
            { _event.value = RoomEvent.SWW }
        )
    }

    fun editPlayerName(playerId: String, newName: String, position: Int) {
        executeUseCase(
            { updatePlayerNameUseCase.execute(playerId, newName) },
            { _event.value = RoomEvent.UpdatedPlayerName(newName, position) },
            { _event.value = RoomEvent.SWW }
        )
    }

    fun resetPoints(playerId: String, position: Int) {
        executeUseCase(
            { updatePointsUseCase.execute(playerId, 0) },
            { _event.value = RoomEvent.RestartedPlayerPoints(position) },
            { _event.value = RoomEvent.SWW }
        )
    }
}