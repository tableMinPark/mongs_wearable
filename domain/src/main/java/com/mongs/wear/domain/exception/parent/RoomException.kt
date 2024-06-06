package com.mongs.wear.domain.exception.parent

import com.mongs.wear.domain.error.ErrorCode

class RoomException (
    errorCode: ErrorCode,
): RepositoryException(errorCode)