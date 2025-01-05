package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class PoopCleanMongException(mongId: Long) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_POOP_CLEAN_MONG,
    result = Collections.singletonMap("mongId", mongId),
)