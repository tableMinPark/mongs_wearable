package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class CreateMongException(name: String, sleepStart: String, sleepEnd: String) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_CREATE_MONG,
    result = mapOf("name" to name, "sleepStart" to sleepStart, "sleepEnd" to sleepEnd),
)