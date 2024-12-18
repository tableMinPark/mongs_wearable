package com.mongs.wear.data.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DataCommonErrorCode(
    private val message: String,
) : ErrorCode {

    COMMON_MQTT_CONNECT_FAIL("MQTT 연결 실패"),
    COMMON_MQTT_PUB_FAIL("MQTT 전송 실패"),
    COMMON_MQTT_SUB_FAIL("MQTT 구독 실패"),
    COMMON_MQTT_PAUSE_FAIL("MQTT 구독 일시 중지 실패"),
    COMMON_MQTT_RESUME_FAIL("MQTT 구독 복구 실패"),
    COMMON_MQTT_DIS_SUB_FAIL("MQTT 구독 해제 실패"),
    COMMON_MQTT_DISCONNECT_FAIL("MQTT 연결 해제 실패"),
    ;

    override fun getMessage(): String {
        return this.message
    }
}