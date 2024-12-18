package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.manager.enums.DataManagerErrorCode

class InvalidCreateMongException(name: String, sleepStart: String, sleepEnd: String) : ErrorException(
    message = DataManagerErrorCode.MANAGER_CREATE_MONG_FAIL.getMessage(),
    result = mapOf(Pair("name", name), Pair("sleepStart", sleepStart), Pair("sleepEnd", sleepEnd)),
)