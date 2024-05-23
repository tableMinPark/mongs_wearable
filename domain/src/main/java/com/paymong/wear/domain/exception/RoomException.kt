package com.paymong.wear.domain.exception

import com.paymong.wear.domain.error.ErrorCode

class RoomException (
    errorCode: ErrorCode,
): ErrorException(errorCode)