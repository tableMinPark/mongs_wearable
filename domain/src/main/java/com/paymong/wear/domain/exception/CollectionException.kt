package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class CollectionException (
    errorCode: ErrorCode,
): ErrorException(errorCode)