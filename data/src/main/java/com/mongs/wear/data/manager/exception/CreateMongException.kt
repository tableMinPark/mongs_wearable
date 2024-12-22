package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ManagerErrorCode

class CreateMongException(name: String, sleepStart: String, sleepEnd: String) : ErrorException(
    code = ManagerErrorCode.MANAGER_CREATE_MONG,
    result = mapOf("name" to name, "sleepStart" to sleepStart, "sleepEnd" to sleepEnd),
)