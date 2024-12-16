package com.mongs.wear.data_.manager.enums

enum class MongStatusCode(
    val message: String,
) {
    NORMAL("정상"),
    SOMNOLENCE("졸림"),
    HUNGRY("배고픔"),
    SICK("아픔"),
    ;
}