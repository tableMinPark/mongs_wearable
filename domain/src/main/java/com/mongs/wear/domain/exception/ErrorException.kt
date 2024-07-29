package com.mongs.wear.domain.exception

import com.mongs.wear.domain.error.ErrorCode

open class ErrorException (
    val errorCode: ErrorCode,
    val throwable: Throwable = Throwable(),
): RuntimeException()