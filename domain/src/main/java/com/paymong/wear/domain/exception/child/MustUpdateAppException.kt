package com.paymong.wear.domain.exception.child

import com.paymong.wear.domain.error.ErrorCode
import com.paymong.wear.domain.exception.parent.UseCaseException

class MustUpdateAppException (
    errorCode: ErrorCode,
): UseCaseException(errorCode)