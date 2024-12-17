package com.mongs.wear.data.manager.enums

import com.mongs.wear.core.enums.ErrorCode

enum class ManagerErrorCode(
    private val message: String,
) : ErrorCode {


    ;

    override fun getMessage(): String {
        return this.message
    }
}