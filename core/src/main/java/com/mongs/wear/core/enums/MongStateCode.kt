package com.mongs.wear.core.enums

enum class MongStateCode(
    val message: String,
) {
    EMPTY("없음"),
    NORMAL("정상"),
    GRADUATE_READY("졸업 대기"),
    EVOLUTION_READY("진화 대기"),
    DEAD("죽음"),
    GRADUATE("졸업"),
    DELETE("삭제"),
    ;
}