package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class PoopCleanMongException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_POOP_CLEAN_MONG,
    result = result,
)