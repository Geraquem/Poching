package com.mmfsin.pochounter.presentation.room.interfaces

interface IPlayersListener {
    fun updatePoints(playerId: String, points: Int, isError: Boolean)
    fun playerSettings(playerId: String, name: String, position: Int)
}