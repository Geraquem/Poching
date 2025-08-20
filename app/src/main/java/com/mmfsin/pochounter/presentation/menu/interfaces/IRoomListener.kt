package com.mmfsin.pochounter.presentation.menu.interfaces

interface IRoomListener {
    fun clickRoom(roomId: String)
    fun longClickRoom(roomId: String, position: Int)
}