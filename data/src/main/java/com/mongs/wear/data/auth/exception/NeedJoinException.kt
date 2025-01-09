package com.mongs.wear.data.auth.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class NeedJoinException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_AUTH_NEED_JOIN,
    result = result,
)
