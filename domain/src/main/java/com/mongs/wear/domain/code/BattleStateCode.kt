package com.mongs.wear.domain.code

enum class BattleStateCode(
    val code: String,
    val message: String,
) {
    NONE("BT000", "없음"),
    DEFENCE("BT001", "방어"),
    DAMAGE("BT002", "공격"),
    DAMAGE_AND_HEAL("BT003", "공격&회복"),
    HEAL("BT003", "회복")
}