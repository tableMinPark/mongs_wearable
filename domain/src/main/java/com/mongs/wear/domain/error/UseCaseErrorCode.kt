package com.mongs.wear.domain.error

enum class UseCaseErrorCode(
    val message: String
) : ErrorCode {
    MUST_UPDATE_APP("앱 업데이트가 필요합니다."),

    ;
    override fun message(): String {
        return message
    }
}