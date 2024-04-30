package com.paymong.wear.domain.error

enum class FeedbackErrorCode(
    val message: String
) : ErrorCode {
    REGISTER_FEEDBACK_FAIL("피드백 등록 실패"),
    ;
    override fun message(): String {
        return message
    }
}