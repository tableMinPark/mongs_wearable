package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.errors.ManagerErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class StrokeMongException(mongId: Long) : ErrorException(
    code = ManagerErrorCode.MANAGER_STROKE_MONG,
    result = Collections.singletonMap("mongId", mongId),
)