package com.mmfsin.pochounter.domain.models

data class Room(
    val id: String,
    val name: String,
    val totalPlayers: Int,
    val players: List<Player>,
    val points: Points,
    val creation: String,
)

data class Player(
    val id: String,
    val roomId: String,
    val name: String,
    val points: Int,
)

data class Points(
    val pointsOkBase: Int,
    val pointsOkExtra: Int,
    val pointsKo: Int,
)