package com.paymong.wear.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "character")
data class Character(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var level: Int = -1
)
