package com.mongs.wear.domain.repositroy

interface FeedbackRepository {

    suspend fun createFeedback(title: String, content: String)
}