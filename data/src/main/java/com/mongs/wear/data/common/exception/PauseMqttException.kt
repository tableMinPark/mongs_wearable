package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.CommonErrorCode
import java.util.Collections

class PauseMqttException : ErrorException(
    code = CommonErrorCode.COMMON_MQTT_PAUSE,
    result = Collections.emptyMap(),
)
