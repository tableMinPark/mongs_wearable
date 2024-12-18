package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.manager.enums.DataManagerErrorCode
import java.util.Collections

class InvalidDeleteMongException(mongId: Long) : ErrorException(
    message = DataManagerErrorCode.MANAGER_DELETE_MONG_FAIL.getMessage(),
    result = Collections.singletonMap("mongId", mongId),
)