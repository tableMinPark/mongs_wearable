package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException

class PickMatchException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_PICK_MATCH,
    result = result,
)