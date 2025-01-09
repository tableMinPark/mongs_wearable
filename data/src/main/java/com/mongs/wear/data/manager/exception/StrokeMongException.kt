package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class StrokeMongException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_STROKE_MONG,
    result = result,
)