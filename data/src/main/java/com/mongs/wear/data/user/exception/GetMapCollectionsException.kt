package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class GetMapCollectionsException : ErrorException(
    code = DataErrorCode.DATA_USER_COLLECTION_GET_MAP_COLLECTIONS,
    result = Collections.emptyMap(),
)
