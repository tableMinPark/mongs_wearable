package com.mongs.wear.data.common.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.data.auth.enums.DataCommonErrorCode
import java.util.Collections

class InvalidDisconnectMqttException() : ErrorException(
    message = DataCommonErrorCode.COMMON_MQTT_DISCONNECT_FAIL.getMessage(),
    result = Collections.emptyMap(),
)
