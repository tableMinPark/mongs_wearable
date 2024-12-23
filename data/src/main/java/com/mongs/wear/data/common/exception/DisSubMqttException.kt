package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.CommonErrorCode
import java.util.Collections

class DisSubMqttException : ErrorException(
    code = CommonErrorCode.DATA_COMMON_MQTT_DIS_SUB,
    result = Collections.emptyMap(),
)
