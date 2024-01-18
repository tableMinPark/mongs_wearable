package com.paymong.wear.domain.processCode

enum class InitLandingProcessCode(
    val message: String
) {
    LOGIN(""),
    GOOGLE_SIGN_IN_CHECK(""),
    GOOGLE_SIGN_IN_FAIL("구글 로그인이 필요합니다,"),
    MONG_LIFE_LOGIN_FAIL("잠시후 다시 시도해 주세요."),
    LOGIN_SUCCESS(""),
    NAV_MAIN(""),
    STAND_BY("")
}