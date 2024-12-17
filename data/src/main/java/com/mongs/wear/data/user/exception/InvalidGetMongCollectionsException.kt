package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidGetMongCollectionsException : ErrorException(
    message = UserErrorCode.USER_GET_MONG_COLLECTIONS_FAIL.getMessage(),
    result = Collections.emptyMap(),
)
