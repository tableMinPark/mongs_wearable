package com.paymong.wear.data.room.feedbackCode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback_code")
data class FeedbackCode(
    @PrimaryKey
    var code: String = "",
    var message: String = "",
    var groupCode: String = "",
    var buildVersion: String = "0.0.0",
)
