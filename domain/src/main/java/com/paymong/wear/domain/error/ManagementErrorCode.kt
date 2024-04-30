package com.paymong.wear.domain.error

enum class ManagementErrorCode(val message: String) : ErrorCode {
    INIT_SET_NOW_SLOT_FAIL("슬롯 초기화 실패"),
    STROKE_FAIL("쓰다 듬기 실패"),
    FEED_FAIL("쓰다 듬기 실패"),
    SLEEPING_FAIL("쓰다 듬기 실패"),
    POOP_CLEAN_FAIL("쓰다 듬기 실패"),
    ;
    override fun message(): String {
        return message
    }
}