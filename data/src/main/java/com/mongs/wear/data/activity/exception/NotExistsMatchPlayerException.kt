package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class NotExistsMatchPlayerException() : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_NOT_EXISTS_MATCH_PLAYER,
)