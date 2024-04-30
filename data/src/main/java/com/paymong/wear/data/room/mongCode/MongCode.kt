package com.paymong.wear.data.room.mongCode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mong_code")
data class MongCode(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var buildVersion: String = "0.0.0",
)