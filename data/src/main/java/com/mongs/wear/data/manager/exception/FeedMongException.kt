package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class FeedMongException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_MANAGER_MANAGEMENT_FEED_MONG,
    result = result,
)