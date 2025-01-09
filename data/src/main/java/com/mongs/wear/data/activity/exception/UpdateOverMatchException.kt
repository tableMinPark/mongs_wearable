package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException

class UpdateOverMatchException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_UPDATE_OVER_MATCH,
    result = result,
)