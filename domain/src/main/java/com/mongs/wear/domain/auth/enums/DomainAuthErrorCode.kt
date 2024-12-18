package com.mongs.wear.domain.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainAuthErrorCode(
    private val message: String,
) : ErrorCode {

    DOMAIN_NOT_EXISTS_EMAIL("로그인을 위한 이메일이 없습니다."),
    DOMAIN_NOT_EXISTS_NAME("로그인을 위한 이름이 없습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}