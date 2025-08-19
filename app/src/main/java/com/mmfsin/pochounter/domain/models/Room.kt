package com.mmfsin.pochounter.domain.models

data class Room(
    val id: String,
    val name: String,
    val totalPlayers: Int,
    val players: List<String>
)