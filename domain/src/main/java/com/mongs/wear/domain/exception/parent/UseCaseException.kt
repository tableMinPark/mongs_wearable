package com.mongs.wear.domain.exception.parent

import com.mongs.wear.domain.error.ErrorCode

open class UseCaseException (
    errorCode: ErrorCode,
): ErrorException(errorCode)