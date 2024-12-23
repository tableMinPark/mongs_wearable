package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ManagerErrorCode
import java.util.Collections

class SleepMongException(mongId: Long) : ErrorException(
    code = ManagerErrorCode.DATA_MANAGER_SLEEP_MONG,
    result = Collections.singletonMap("mongId", mongId),
)