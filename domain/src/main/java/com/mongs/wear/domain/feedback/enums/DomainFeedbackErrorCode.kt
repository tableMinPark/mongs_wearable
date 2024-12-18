package com.mongs.wear.domain.feedback.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainFeedbackErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}