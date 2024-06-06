package com.mongs.wear.domain.exception.parent

import com.mongs.wear.domain.error.ErrorCode

class ApiException (
    errorCode: ErrorCode,
): RepositoryException(errorCode)