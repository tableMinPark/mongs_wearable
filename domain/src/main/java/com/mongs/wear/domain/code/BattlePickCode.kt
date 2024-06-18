package com.mongs.wear.domain.code

enum class BattlePickCode(
    val code: String,
    val message: String,
) {
    ATTACK("BP000", "없음"),
    DEFENCE("BP001", "방어"),
    HEAL("BP002", "회복")
}