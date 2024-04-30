package com.paymong.wear.domain.processCode

enum class FeedSelectProcessCode(
    val message: String
) {
    LOAD_FEED_ITEM_LIST_END(""),
    LOAD_FEED_ITEM_LIST_FAIL("잠시후 다시 시도 해주세요."),
    FEED_FAIL("구매 실패"),
    CANT_BUY_FEED_ITEM("지금은 구매할 수 없습니다."),
    STAND_BY(""),
}