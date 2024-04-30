package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class AuthException (
    errorCode: ErrorCode,
): ErrorException(errorCode)