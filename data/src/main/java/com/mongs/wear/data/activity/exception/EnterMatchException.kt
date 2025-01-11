package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException

class EnterMatchException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_ENTER_MATCH,
    result = result,
)