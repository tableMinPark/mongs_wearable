package com.mongs.wear.domain.feedback.repository

interface FeedbackRepository {

    /**
     * 오류 신고 등록
     */
    suspend fun createFeedback(title: String, content: String)
}