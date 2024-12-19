package com.mongs.wear.domain.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainAuthErrorCode(
    private val message: String,
) : ErrorCode {

    DOMAIN_LOGIN_FAIL("로그인에 실패했습니다."),
    DOMAIN_LOGOUT_FAIL("로그인에 실패했습니다."),

    DOMAIN_NOT_EXISTS_EMAIL("이메일이 존재하지 않습니다."),
    DOMAIN_NOT_EXISTS_NAME("이름이 존재하지 않습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}