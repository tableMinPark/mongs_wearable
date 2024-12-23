package com.mongs.wear.core.errors

enum class CommonErrorCode(
    private val message: String,
) : ErrorCode {

    DATA_COMMON_MQTT_CONNECT("MQTT 연결 실패"),
    DATA_COMMON_MQTT_PUB("MQTT 전송 실패"),
    DATA_COMMON_MQTT_SUB("MQTT 구독 실패"),
    DATA_COMMON_MQTT_PAUSE("MQTT 구독 일시 중지 실패"),
    DATA_COMMON_MQTT_RESUME("MQTT 구독 복구 실패"),
    DATA_COMMON_MQTT_DIS_SUB("MQTT 구독 해제 실패"),
    DATA_COMMON_MQTT_DISCONNECT("MQTT 연결 해제 실패"),
    ;

    override fun getMessage(): String {
        return this.message
    }
}