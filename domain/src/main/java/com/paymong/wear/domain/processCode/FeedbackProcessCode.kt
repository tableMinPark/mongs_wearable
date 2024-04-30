package com.paymong.wear.domain.processCode

enum class FeedbackProcessCode(
    val message: String
) {
    LOAD_FEEDBACK_ITEM_LIST_END(""),
    LOAD_FEEDBACK_ITEM_LIST_FAIL("잠시후 다시 시도해주세요."),
    FEEDBACK_FAIL("피드백 등록 실패"),
    STAND_BY(""),
}