package com.paymong.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_code")
data class FoodCode(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var groupCode: String = "",
    var price: Int = 0,
    val addWeight: Double = 0.0,
    val addStrength: Double = 0.0,
    val addSatiety: Double = 0.0,
    val addHealthy: Double = 0.0,
    val addSleep: Double = 0.0,
    var buildVersion: String = "0.0.0",
)
