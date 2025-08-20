package com.mmfsin.pochounter.presentation.menu

import com.mmfsin.pochounter.base.BaseViewModel
import com.mmfsin.pochounter.domain.models.Points
import com.mmfsin.pochounter.domain.usecases.CreateRoomUseCase
import com.mmfsin.pochounter.domain.usecases.GetRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val createRoomUseCase: CreateRoomUseCase
) : BaseViewModel<MenuEvent>() {

    fun getRooms() {
        executeUseCase(
            { getRoomsUseCase.execute() },
            { result -> _event.value = MenuEvent.GetRooms(result) },
            { _event.value = MenuEvent.SWW }
        )
    }

    fun createRoom(roomName: String, points: Points, players: List<String>) {
        executeUseCase(
            { createRoomUseCase.execute(roomName, points, players) },
            { result -> _event.value = MenuEvent.RoomCreated(result) },
            { _event.value = MenuEvent.SWW }
        )
    }
}