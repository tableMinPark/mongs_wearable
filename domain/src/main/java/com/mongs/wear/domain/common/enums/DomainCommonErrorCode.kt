package com.mongs.wear.domain.common.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainCommonErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}