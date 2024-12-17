package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.CommonErrorCode
import java.util.Collections

class InvalidPubMqttException() : ErrorException(
    message = CommonErrorCode.COMMON_MQTT_PUB_FAIL.getMessage(),
    result = Collections.emptyMap(),
)