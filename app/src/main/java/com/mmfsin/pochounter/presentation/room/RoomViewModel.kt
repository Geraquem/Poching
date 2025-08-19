package com.mmfsin.pochounter.presentation.room

import com.mmfsin.pochounter.base.BaseViewModel
import com.mmfsin.pochounter.domain.usecases.GetRoomDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRoomDataUseCase: GetRoomDataUseCase,
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
}