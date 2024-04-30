package com.paymong.wear.domain.processCode

enum class MainConfigureProcessCode(
    val message: String
) {
    LOGOUT(""),
    MONGS_LOGOUT_FAIL("잠시후 다시 시도해 주세요."),
    GOOGLE_SIGN_OUT_FAIL("잠시후 다시 시도해 주세요."),
    LOGOUT_SUCCESS(""),
    NAV_INIT_LANDING(""),
    STAND_BY("")
}