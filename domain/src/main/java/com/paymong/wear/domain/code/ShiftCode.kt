package com.paymong.wear.domain.code

enum class ShiftCode(
    val code: String,
    val message: String,
) {
    NORMAL("SH000", "기본"),
    DEAD("SH001", "죽음"),
    GRADUATION_READY("SH002", "졸업 대기"),
    EVOLUTION_READY("SH003", "진화 대기"),
    EVOLUTION("SH004", "진화 중"),
    EMPTY("SH444", "없음"),
    DELETE("SH999", "삭제")
}