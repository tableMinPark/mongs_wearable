package com.mongs.wear.domain.code

enum class MatchStateCode(
    val code: String,
    val message: String,
) {
    NONE("MS000", "없음"),
    ENTER("MS001", "입장"),
    MATCH("MS002", "메치"),
    OVER("MS003", "종료"),
}