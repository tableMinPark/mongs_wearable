package com.mongs.wear.data_.manager.enums

enum class MongStateCode(
    val message: String,
) {
    NORMAL("정상"),
    GRADUATE_READY("졸업 대기"),
    EVOLUTION_READY("진화 대기"),
    DEAD("죽음"),
    ;
}