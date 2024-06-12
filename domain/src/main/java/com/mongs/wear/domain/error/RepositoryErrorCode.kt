package com.mongs.wear.domain.error

enum class RepositoryErrorCode(
    val message: String
) : ErrorCode {
    LOGIN_FAIL("로그인 실패"),
    LOGOUT_FAIL("로그아웃 실패"),
    REISSUE_FAIL("토큰 재발급 실패"),

    VALIDATION_VERSION_FAIL("버전 확인 실패"),
    SET_CODES_FAIL(""),
    GET_MONG_CODE_FAIL(""),
    GET_FOOD_CODES_FAIL(""),
    GET_SNACK_CODES_FAIL(""),

    GET_MAP_COLLECTIONS_FAIL(""),
    GET_MONG_COLLECTIONS_FAIL(""),

    SET_BUILD_VERSION_FAIL(""),
    GET_BUILD_VERSION_FAIL(""),
    SET_CODE_INTEGRITY_FAIL(""),
    GET_CODE_INTEGRITY_FAIL(""),
    SET_DEVICE_ID_FAIL(""),
    GET_DEVICE_ID_FAIL(""),
    SET_BACKGROUND_MAP_CODE_FAIL(""),
    GET_BACKGROUND_MAP_CODE_FAIL(""),

    ADD_FEEDBACK_LOG_FAIL(""),
    ADD_FEEDBACK_FAIL(""),

    SYNC_SLOT_FAIL(""),
    SET_SLOTS_FAIL(""),
    GET_SLOTS_LIVE_FAIL(""),
    SET_NOW_SLOT_FAIL(""),
    GET_NOT_SLOT_FAIL(""),
    GET_NOT_SLOT_LIVE_FAIL(""),

    ADD_MONG_FAIL(""),
    DELETE_MONG_FAIL(""),
    GET_FEED_LOG_FAIL(""),
    FEED_MONG_FAIL(""),
    GRADUATE_MONG_FAIL(""),
    GRADUATE_MONG_READY_FAIL(""),
    EVOLUTION_MONG_FAIL(""),
    SLEEPING_MONG_FAIL(""),
    STROKE_MONG_FAIL(""),
    POOP_CLEAN_MONG_FAIL(""),
    SET_IS_HAPPY_FAIL(""),
    SET_IS_EATING_FAIL(""),


    SET_MEMBER_FAIL(""),
    BUY_SLOT_FAIL(""),
    SET_REFRESH_TOKEN_FAIL(""),
    GET_REFRESH_TOKEN_FAIL(""),
    SET_ACCESS_TOKEN_FAIL(""),
    GET_ACCESS_TOKEN_FAIL(""),
    SET_MAX_SLOT_FAIL(""),
    GET_MAX_SLOT_FAIL(""),
    SET_STAR_POINT_FAIL(""),
    GET_STAR_POINT_FAIL(""),
    SET_WALKING_COUNT_FAIL(""),
    GET_WALKING_COUNT_FAIL(""),
    EXCHANGE_PAY_POINT_WALKING_FAIL(""),
    ;
    override fun message(): String {
        return message
    }
}