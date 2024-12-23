package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.errors.ManagerErrorCode
import com.mongs.wear.core.exception.ErrorException

class FeedMongException(mongId: Long, foodTypeCode: String) : ErrorException(
    code = ManagerErrorCode.DATA_MANAGER_FEED_MONG,
    result = mapOf("mongId" to mongId, "foodTypeCode" to foodTypeCode),
)