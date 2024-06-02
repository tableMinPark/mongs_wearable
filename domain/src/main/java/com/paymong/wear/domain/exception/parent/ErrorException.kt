package com.paymong.wear.domain.exception.parent

import com.paymong.wear.domain.error.ErrorCode

open class ErrorException (
    val errorCode: ErrorCode
): RuntimeException()