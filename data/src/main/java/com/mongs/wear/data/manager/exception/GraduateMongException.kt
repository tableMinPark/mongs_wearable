package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ManagerErrorCode
import java.util.Collections

class GraduateMongException(mongId: Long) : ErrorException(
    code = ManagerErrorCode.DATA_MANAGER_GRADUATE_MONG,
    result = Collections.singletonMap("mongId", mongId),
)