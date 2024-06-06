package com.mongs.wear.domain.code

enum class StateCode(
    val code: String,
    val message: String,
) {
    NORMAL("CD000", "정상"),
    TIRED("CD001", "졸림"),
    HUNGRY("CD002", "배고픔"),
    SICK("CD003", "아픔"),
    EMPTY("CD444", "없음")
}