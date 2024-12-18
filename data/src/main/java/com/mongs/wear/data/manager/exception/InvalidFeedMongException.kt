package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.manager.enums.DataManagerErrorCode

class InvalidFeedMongException(mongId: Long, foodTypeCode: String) : ErrorException(
    message = DataManagerErrorCode.MANAGER_FEED_MONG_FAIL.getMessage(),
    result = mapOf(Pair("mongId", mongId), Pair("foodTypeCode", foodTypeCode)),
)