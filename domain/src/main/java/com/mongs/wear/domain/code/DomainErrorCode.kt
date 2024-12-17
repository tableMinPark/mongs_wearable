package com.mongs.wear.data.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainErrorCode(
    private val message: String,
) : ErrorCode {

    DOMAIN_LOGIN_FAIL("로그인에 실패했습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}