package com.mmfsin.pochounter.presentation.menu

import com.mmfsin.pochounter.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
) : BaseViewModel<MenuEvent>() {

    fun getCreatedGames() {
//        executeUseCase(
//            { joinRoomUseCase.execute(roomCode) },
//            { result ->
//                if (result == null) _event.value = MenuEvent.SWW
//                else _event.value = MenuEvent.JoinedRoom(result)
//            },
//            { _event.value = MenuEvent.SWW }
//        )
    }
}