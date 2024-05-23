package com.paymong.wear.domain.error

enum class RepositoryErrorCode(
    val message: String
) : ErrorCode {
    LOGIN_FAIL("로그인 실패"),
    LOGOUT_FAIL("로그아웃 실패"),
    REISSUE_FAIL("토큰 재발급 실패"),

    VALIDATION_VERSION_FAIL("버전 확인 실패"),
    SYNC_CODE_FAIL("코드 값 초기화 실패"),

    FIND_MAP_COLLECTION_FAIL(""),
    FIND_MONG_COLLECTION_FAIL(""),

    FIND_MEMBER_FAIL(""),
    BUY_SLOT_FAIL(""),

    FEEDBACK_FAIL(""),

    SYNC_MONG_FAIL(""),
    NOT_FOUND_MONG_FAIL(""),
    NOT_FOUND_SELECTED_SLOT(""),
    ADD_MONG_FAIL(""),
    DELETE_MONG_FAIL(""),
    NOT_FOUND_FEED_LOG(""),
    FEED_MONG_FAIL(""),
    GRADUATE_MONG_FAIL(""),
    EVOLUTION_MONG_FAIL(""),
    SLEEPING_MONG_FAIL(""),
    STROKE_MONG_FAIL(""),
    POOP_CLEAN_FAIL(""),
    ;
    override fun message(): String {
        return message
    }
}