package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class ApiException (
    errorCode: ErrorCode,
): ErrorException(errorCode)