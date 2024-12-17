package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.user.enums.UserErrorCode
import java.util.Collections

class InvalidUpdatePlayerException : ErrorException(
    message = UserErrorCode.USER_UPDATE_PLAYER_FAIL.getMessage(),
    result = Collections.emptyMap(),
)
