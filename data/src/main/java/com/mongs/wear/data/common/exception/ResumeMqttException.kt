package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.CommonErrorCode
import java.util.Collections

class ResumeMqttException : ErrorException(
    code = CommonErrorCode.DATA_COMMON_MQTT_RESUME,
    result = Collections.emptyMap(),
)
