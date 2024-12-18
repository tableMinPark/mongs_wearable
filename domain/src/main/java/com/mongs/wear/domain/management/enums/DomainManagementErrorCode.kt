package com.mongs.wear.domain.management.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainManagementErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}