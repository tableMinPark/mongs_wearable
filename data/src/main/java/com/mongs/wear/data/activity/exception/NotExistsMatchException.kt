package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ActivityErrorCode
import java.util.Collections

class NotExistsMatchException(deviceId: String) : ErrorException(
    code = ActivityErrorCode.DATA_ACTIVITY_NOT_EXISTS_MATCH,
    result = Collections.singletonMap("deviceId", deviceId),
)