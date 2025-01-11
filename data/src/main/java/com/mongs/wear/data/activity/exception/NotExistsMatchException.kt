package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException

class NotExistsMatchException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_NOT_EXISTS_MATCH,
    result = result,
)