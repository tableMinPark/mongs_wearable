package com.mongs.wear.domain.code

enum class ShiftCode(
    val code: String,
    val message: String,
) {
    NORMAL("SH000", "기본"),
    DEAD("SH001", "죽음"),
    GRADUATE_READY("SH002", "졸업 대기"),
    EVOLUTION_READY("SH003", "진화 대기"),
    GRADUATE("SH004", "졸업"),
    EMPTY("SH444", "없음"),
    DELETE("SH999", "삭제"),
    ;
}