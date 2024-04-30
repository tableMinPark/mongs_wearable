package com.paymong.wear.data.repository.code

import com.paymong.wear.domain.code.StateCode

enum class State(
    val code: StateCode
) {
    CD000(StateCode.NORMAL),
    CD001(StateCode.TIRED),
    CD002(StateCode.HUNGRY),
    CD003(StateCode.SICK),
    CD444(StateCode.EMPTY),
}