package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.CommonErrorCode
import java.util.Collections

class InvalidPauseMqttException() : ErrorException(
    message = CommonErrorCode.COMMON_MQTT_PAUSE_FAIL.getMessage(),
    result = Collections.emptyMap(),
)
