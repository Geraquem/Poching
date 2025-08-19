package com.mmfsin.pochounter.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RoomDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var totalPlayers: Int = 0
}