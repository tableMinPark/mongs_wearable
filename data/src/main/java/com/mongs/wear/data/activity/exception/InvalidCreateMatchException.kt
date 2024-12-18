package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode
import java.util.Collections

class InvalidCreateMatchException(mongId: Long) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_DELETE_MATCH_FAIL.getMessage(),
    result = Collections.singletonMap("mongId", mongId),
)