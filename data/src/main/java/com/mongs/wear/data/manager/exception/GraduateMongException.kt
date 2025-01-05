package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class GraduateMongException(mongId: Long) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_GRADUATE_MONG,
    result = Collections.singletonMap("mongId", mongId),
)