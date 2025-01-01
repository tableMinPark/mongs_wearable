package com.mongs.wear.domain.management.vo

data class SlotVo(

    val code: SlotCode = SlotCode.EMPTY,

    val mongVo: MongVo? = null
) {
    enum class SlotCode {
        EMPTY,
        BUY_SLOT,
        EXISTS,
    }
}