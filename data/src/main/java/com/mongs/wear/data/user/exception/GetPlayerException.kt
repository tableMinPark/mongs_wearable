package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode
import java.util.Collections

class GetPlayerException(result: Map<String, Any>) : ErrorException(
    code = DataErrorCode.DATA_USER_PLAYER_GET_PLAYER,
    result = result,
)