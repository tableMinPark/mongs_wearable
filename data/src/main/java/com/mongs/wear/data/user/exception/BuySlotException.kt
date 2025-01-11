package com.mongs.wear.data.user.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class BuySlotException(result: Map<String, Any> = emptyMap()) : ErrorException(
    code = DataErrorCode.DATA_USER_PLAYER_BUY_SLOT,
    result = result,
)
