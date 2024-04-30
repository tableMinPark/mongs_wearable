package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class FeedbackException (
    errorCode: ErrorCode,
): ErrorException(errorCode)