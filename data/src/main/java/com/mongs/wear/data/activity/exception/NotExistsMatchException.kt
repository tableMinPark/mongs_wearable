package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class NotExistsMatchException(deviceId: String) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_NOT_EXISTS_MATCH,
    result = Collections.singletonMap("deviceId", deviceId),
)