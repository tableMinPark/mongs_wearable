package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode
import com.mongs.wear.core.errors.ErrorCode
import java.util.Collections

class CreateMatchException(mongId: Long) : ErrorException(
    code = ActivityErrorCode.ACTIVITY_CREATE_MATCH,
    result = Collections.singletonMap("mongId", mongId),
)