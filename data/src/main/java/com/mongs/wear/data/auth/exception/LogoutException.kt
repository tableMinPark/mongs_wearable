package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class LogoutException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_AUTH_LOGOUT,
    result = result,
)
