package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.CommonErrorCode
import java.util.Collections

class ConnectMqttException : ErrorException(
    code = CommonErrorCode.DATA_COMMON_MQTT_CONNECT,
    result = Collections.emptyMap(),
)
