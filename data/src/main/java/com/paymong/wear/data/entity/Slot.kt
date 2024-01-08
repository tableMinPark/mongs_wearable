package com.paymong.wear.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "slot")
data class Slot(
    @PrimaryKey(autoGenerate = true)
    var slotId: Long = 0L,
    var mongId: Long = -1L,
    var born: LocalDateTime = LocalDateTime.now(),
    var weight: Int = 0,
    var mongCode: String = "CH444",
    var nextMongCode: String = "CH444",

    var stateCode: String = "CD444",
    var nextStateCode: String = "CD444",
    var poopCount: Int = 0,

    var health: Float = 0f,
    var satiety: Float = 0f,
    var strength: Float = 0f,
    var sleep: Float = 0f,
)
