package com.mongs.wear.domain.exception

import com.mongs.wear.domain.error.ErrorCode

open class UseCaseException (
    errorCode: ErrorCode,
): ErrorException(errorCode)