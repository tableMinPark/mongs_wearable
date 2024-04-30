package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class CommonException (
    errorCode: ErrorCode,
): ErrorException(errorCode)