package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.UserErrorCode
import java.util.Collections

class GetMongCollectionsException : ErrorException(
    code = UserErrorCode.USER_GET_MONG_COLLECTIONS,
    result = Collections.emptyMap(),
)
