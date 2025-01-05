package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class ReissueException : ErrorException(
    code = DataErrorCode.DATA_AUTH_REISSUE,
)
