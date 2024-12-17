package com.mongs.wear.data.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class AuthErrorCode(
    private val message: String,
) : ErrorCode {

    AUTH_LOGIN_FAIL("로그인 실패"),
    AUTH_REISSUE_FAIL("토큰 재발행 실패"),
    AUTH_LOGOUT_FAIL("로그인 실패"),
    ;

    override fun getMessage(): String {
        return this.message
    }
}