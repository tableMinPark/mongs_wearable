package com.mongs.wear.data.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class ActivityErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}