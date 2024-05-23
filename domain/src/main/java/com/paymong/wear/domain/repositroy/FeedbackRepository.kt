package com.paymong.wear.domain.repositroy

interface FeedbackRepository {
    suspend fun addFeedbackLog(groupCode: String, location: String, message: String)
    suspend fun addFeedback(groupCode: String, message: String)
}