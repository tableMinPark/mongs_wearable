package com.mongs.wear.data.user.exception

import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import java.util.Collections

class UpdatePlayerException : ErrorException(
    code = UserErrorCode.DATA_USER_UPDATE_PLAYER,
    result = Collections.emptyMap(),
)
