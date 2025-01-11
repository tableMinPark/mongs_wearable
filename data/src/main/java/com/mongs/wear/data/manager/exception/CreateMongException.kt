package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class CreateMongException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_CREATE_MONG,
    result = result,
)