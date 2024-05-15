package com.paymong.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map_code")
data class MapCode(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var buildVersion: String = "0.0.0",
)
