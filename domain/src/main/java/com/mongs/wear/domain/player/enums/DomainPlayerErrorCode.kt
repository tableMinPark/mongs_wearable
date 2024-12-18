package com.mongs.wear.domain.player.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainPlayerErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}