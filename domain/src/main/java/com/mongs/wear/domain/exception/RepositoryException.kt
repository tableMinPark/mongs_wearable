package com.mongs.wear.domain.exception

import com.mongs.wear.domain.error.ErrorCode

open class RepositoryException (
    errorCode: ErrorCode,
    throwable: Throwable = Throwable(),
): ErrorException(errorCode, throwable)