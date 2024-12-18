package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataActivityErrorCode
import java.util.Collections

class NotExistsMatchException(deviceId: String) : ErrorException(
    message = DataActivityErrorCode.ACTIVITY_NOT_EXISTS_MATCH.getMessage(),
    result = Collections.singletonMap("deviceId", deviceId),
)