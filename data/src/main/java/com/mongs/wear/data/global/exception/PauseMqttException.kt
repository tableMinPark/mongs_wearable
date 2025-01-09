package com.mongs.wear.data.global.exception

import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.errors.DataErrorCode

class PauseMqttException : ErrorException(
    code = DataErrorCode.DATA_GLOBAL_MQTT_PAUSE,
)