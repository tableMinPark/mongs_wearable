package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode
import java.util.Collections

class CreateMatchException(mongId: Long) : ErrorException(
    code = ActivityErrorCode.DATA_ACTIVITY_CREATE_MATCH,
    result = Collections.singletonMap("mongId", mongId),
)