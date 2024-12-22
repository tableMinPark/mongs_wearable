package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.CommonErrorCode
import java.util.Collections

class SubMqttException : ErrorException(
    code = CommonErrorCode.COMMON_MQTT_SUB,
    result = Collections.emptyMap(),
)
