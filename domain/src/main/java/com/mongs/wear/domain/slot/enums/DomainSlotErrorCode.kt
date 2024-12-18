package com.mongs.wear.domain.slot.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainSlotErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}