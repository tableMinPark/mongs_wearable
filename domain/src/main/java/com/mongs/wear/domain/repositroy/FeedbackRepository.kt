package com.mongs.wear.domain.repositroy

import java.time.LocalDateTime

interface FeedbackRepository {
    suspend fun addFeedbackLog(groupCode: String, location: String, message: String)
    suspend fun removeFeedbackLog(createdAt: LocalDateTime)
    suspend fun addFeedback(groupCode: String, message: String)
}