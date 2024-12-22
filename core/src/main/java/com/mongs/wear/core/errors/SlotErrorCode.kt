package com.mongs.wear.core.errors

enum class SlotErrorCode(
    private val message: String,
) : ErrorCode {

    SLOT_GET_CURRENT_SLOT("현재 슬롯 조회에 실패했습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}