package com.paymong.wear.domain.error

enum class AuthErrorCode(
    val message: String
) : ErrorCode {
    LOGIN_FAIL("로그인 실패"),
    LOGOUT_FAIL("로그아웃 실패"),
    REISSUE_FAIL("토큰 재발급 실패"),
    ;
    override fun message(): String {
        return message
    }
}