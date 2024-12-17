package com.mongs.wear.domain.repositroy

interface FeedbackRepository {

    /**
     * 오류 신고 등록
     */
    suspend fun createFeedback(title: String, content: String)
}