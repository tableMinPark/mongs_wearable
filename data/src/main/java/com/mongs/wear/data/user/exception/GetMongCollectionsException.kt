package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class GetMongCollectionsException : ErrorException(
    code = DataErrorCode.DATA_USER_COLLECTION_GET_MONG_COLLECTIONS,
    result = Collections.emptyMap(),
)
