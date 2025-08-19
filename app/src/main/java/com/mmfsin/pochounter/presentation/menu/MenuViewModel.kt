package com.mmfsin.pochounter.presentation.menu

import com.mmfsin.pochounter.base.BaseViewModel
import com.mmfsin.pochounter.domain.usecases.CreateRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val createRoomUseCase: CreateRoomUseCase
) : BaseViewModel<MenuEvent>() {

    fun createRoom(roomName: String) {
        executeUseCase(
            { createRoomUseCase.execute(roomName) },
            { _event.value = MenuEvent.RoomCreated(roomName) },
            { _event.value = MenuEvent.SWW }
        )
    }
}