package com.mongs.wear.data.manager.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.ManagerErrorCode
import java.util.Collections

class GetFeedItemsException(mongId: Long) : ErrorException(
    code = ManagerErrorCode.DATA_MANAGER_GET_FEED_ITEMS,
    result = Collections.singletonMap("mongId", mongId),
)