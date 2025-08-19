package com.mmfsin.pochounter.domain.models

data class Room(
    val id: String,
    val name: String,
    val totalPlayers: Int,
    val players: List<Player>
)

data class Player(
    val id: String,
    val roomId: String,
    val name: String,
    val points: Int,
)