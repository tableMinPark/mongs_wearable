package com.paymong.wear.domain.processCode

enum class CollectionSelectProcessCode(
    val message: String
) {
    LOAD_COLLECTION_ITEM_LIST_END(""),
    LOAD_COLLECTION_ITEM_LIST_FAIL("잠시후 다시 시도 해주세요."),
    CANT_SHOW_DETAIL("수집하지 않은 컬렉션"),
    STAND_BY(""),
}