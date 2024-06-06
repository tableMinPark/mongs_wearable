package com.mongs.wear.domain.exception.parent

import com.mongs.wear.domain.error.ErrorCode

open class ErrorException (
    val errorCode: ErrorCode
): RuntimeException()