package com.mongs.wear.domain.device.exception

import com.mongs.wear.core.exception.UseCaseException
import com.mongs.wear.core.errors.DomainErrorCode

class ConnectMqttException : UseCaseException(
    code = DomainErrorCode.DOMAIN_DEVICE_CONNECT_MQTT_FAILED,
)