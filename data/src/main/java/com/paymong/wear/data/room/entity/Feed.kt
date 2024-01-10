package com.paymong.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "feed")
data class Feed(
    @PrimaryKey
    var code: String = "",
    var feedCode: String = "",
    var name: String = "",
    var price: Int = 0,
    var buyRegDt: LocalDateTime = LocalDateTime.now()
)
