package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.errors.DataErrorCode
import com.mongs.wear.core.exception.ErrorException

class NotExistsMatchPlayerException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_NOT_EXISTS_MATCH_PLAYER,
    result = result,
)