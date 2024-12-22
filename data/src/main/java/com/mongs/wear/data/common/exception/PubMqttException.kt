package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.CommonErrorCode
import java.util.Collections

class PubMqttException : ErrorException(
    code = CommonErrorCode.COMMON_MQTT_PUB,
    result = Collections.emptyMap(),
)
