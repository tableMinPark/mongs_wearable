package com.mongs.wear.domain.exception.child

import com.mongs.wear.domain.error.ErrorCode
import com.mongs.wear.domain.exception.parent.UseCaseException

class MustUpdateAppException (
    errorCode: ErrorCode,
): UseCaseException(errorCode)