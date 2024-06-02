package com.paymong.wear.data.code

import com.paymong.wear.domain.code.ShiftCode

enum class Shift(
    val code: ShiftCode
) {
    SH000(ShiftCode.NORMAL),
    SH001(ShiftCode.DEAD),
    SH002(ShiftCode.GRADUATE_READY),
    SH003(ShiftCode.EVOLUTION_READY),
    SH004(ShiftCode.EVOLUTION),
    SH444(ShiftCode.EMPTY),
    SH999(ShiftCode.DELETE),
}