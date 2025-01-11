package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class DeleteMongException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_DELETE_MONG,
    result = result,
)