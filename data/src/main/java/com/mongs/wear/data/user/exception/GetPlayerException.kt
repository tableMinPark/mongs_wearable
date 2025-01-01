package com.mongs.wear.data.user.exception

import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class GetPlayerException : ErrorException(
    code = UserErrorCode.DATA_USER_GET_PLAYER,
    result = Collections.emptyMap(),
)
