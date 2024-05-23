package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class FailException (
    errorCode: ErrorCode,
): ErrorException(errorCode)