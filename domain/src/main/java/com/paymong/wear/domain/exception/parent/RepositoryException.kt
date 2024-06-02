package com.paymong.wear.domain.exception.parent

import com.paymong.wear.domain.error.ErrorCode

open class RepositoryException (
    errorCode: ErrorCode,
): ErrorException(errorCode)