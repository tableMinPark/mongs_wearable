package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ManagerErrorCode
import java.util.Collections

class PoopCleanMongException(mongId: Long) : ErrorException(
    code = ManagerErrorCode.DATA_MANAGER_POOP_CLEAN_MONG,
    result = Collections.singletonMap("mongId", mongId),
)