package com.paymong.wear.domain.exception.parent

import com.paymong.wear.domain.error.ErrorCode

class RoomException (
    errorCode: ErrorCode,
): RepositoryException(errorCode)