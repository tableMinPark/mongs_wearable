package com.mongs.wear.core.errors

enum class AuthErrorCode(
    private val message: String,
) : ErrorCode {

    AUTH_NEED_JOIN("회원가입이 필요합니다."),
    AUTH_NEED_UPDATE_APP("앱 업데이트가 필요합니다."),
    AUTH_JOIN("회원가입에 실패했습니다."),
    AUTH_LOGIN("로그인에 실패했습니다."),
    AUTH_LOGOUT("로그인에 실패했습니다."),
    AUTH_REISSUE("로그인에 실패했습니다."),

    AUTH_NOT_EXISTS_EMAIL("이메일이 존재하지 않습니다."),
    AUTH_NOT_EXISTS_NAME("이름이 존재하지 않습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}