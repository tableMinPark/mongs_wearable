package com.mongs.wear.data.user.enums

import com.mongs.wear.core.enums.ErrorCode

enum class UserErrorCode(
    private val message: String,
) : ErrorCode {

    USER_GET_MAP_COLLECTIONS_FAIL("컬렉션 맵 조회 실패"),
    USER_GET_MONG_COLLECTIONS_FAIL("컬렉션 맵 조회 실패"),
    USER_CREATE_FEEDBACK_FAIL("오류 신고 실패"),
    USER_UPDATE_PLAYER_FAIL("플레이어 업데이트 실패"),
    USER_BUY_SLOT_FAIL("슬롯 구매 실패"),
    USER_CHARGE_STAR_POINT_FAIL("스타포인트 충전 실패"),
    USER_EXCHANGE_STAR_POINT_FAIL("스타포인트 환전 실패"),
    USER_CHARGE_WALKING_FAIL("걸음 수 충전 실패"),
    USER_EXCHANGE_WALKING_FAIL("걸음 수 환전 실패"),
    ;

    override fun getMessage(): String {
        return this.message
    }
}