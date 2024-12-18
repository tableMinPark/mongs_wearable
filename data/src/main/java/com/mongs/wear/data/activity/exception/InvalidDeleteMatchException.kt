package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode
import java.util.Collections

class InvalidDeleteMatchException(mongId: Long) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_CREATE_MATCH_FAIL.getMessage(),
    result = Collections.singletonMap("mongId", mongId),
)