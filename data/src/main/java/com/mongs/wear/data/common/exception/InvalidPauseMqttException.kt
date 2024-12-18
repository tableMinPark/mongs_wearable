package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataCommonErrorCode
import java.util.Collections

class InvalidPauseMqttException() : ErrorException(
    message = DataCommonErrorCode.COMMON_MQTT_PAUSE_FAIL.getMessage(),
    result = Collections.emptyMap(),
)
