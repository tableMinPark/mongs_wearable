package com.paymong.wear.domain.processCode

enum class InitLandingProcessCode(
    val message: String
) {
    LOGIN(""),
    GOOGLE_SIGN_IN_CHECK(""),
    GOOGLE_SIGN_IN_FAIL("구글 로그인이 필요합니다."),
    MONGS_LOGIN_FAIL("인터넷 연결을 확인 해주세요."),
    LOGIN_SUCCESS(""),
    MUST_UPDATE_APP("앱 업데이트가 필요합니다."),
    NAV_MAIN(""),
    STAND_BY("")
}