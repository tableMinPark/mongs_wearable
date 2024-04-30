package com.paymong.wear.data.room.feedback

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "feedback_log")
data class FeedbackLog(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var groupCode: String = "",
    var message: String = "",
    var location: String = "",
    var createdAt: LocalDateTime = LocalDateTime.now()
)