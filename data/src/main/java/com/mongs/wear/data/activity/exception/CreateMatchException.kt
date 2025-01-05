package com.mongs.wear.data.activity.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class CreateMatchException(mongId: Long) : ErrorException(
    code = DataErrorCode.DATA_ACTIVITY_BATTLE_CREATE_MATCH,
    result = Collections.singletonMap("mongId", mongId),
)