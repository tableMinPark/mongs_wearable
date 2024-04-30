package com.paymong.wear.domain.processCode

enum class SlotSelectProcessCode(
    val message: String
) {
    CHANGE_SLOT_END(""),
    LOAD_SLOT_LIST_END(""),
    BUY_SLOT(""),
    BUY_SLOT_FAIL("잠시후 다시 시도 해주세요."),
    ADD_SLOT(""),
    ADD_SLOT_FAIL("잠시후 다시 시도 해주세요."),
    REMOVE_SLOT(""),
    REMOVE_SLOT_FAIL("잠시후 다시 시도 해주세요."),
    SET_SLOT(""),
    SET_SLOT_FAIL("잠시후 다시 시도 해주세요."),
    NAV_MAIN(""),
    STAND_BY(""),
}