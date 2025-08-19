package com.mmfsin.pochounter.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PlayerDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var roomId: String = ""
    var name: String = ""
    var points: Int = 0
}