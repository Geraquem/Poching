package com.mmfsin.pochounter.presentation.menu.interfaces

import com.mmfsin.pochounter.domain.models.Room

interface IRoomListener {
    fun clickRoom(room: Room)
}