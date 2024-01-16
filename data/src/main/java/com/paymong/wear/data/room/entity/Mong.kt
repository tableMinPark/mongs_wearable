package com.paymong.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mong")
data class Mong(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var level: Int = -1
)
