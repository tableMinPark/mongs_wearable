package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class ManagementException (
    errorCode: ErrorCode,
): ErrorException(errorCode)