package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class GetMapCollectionsException : ErrorException(
    code = UserErrorCode.USER_GET_MAP_COLLECTIONS,
    result = Collections.emptyMap(),
)
