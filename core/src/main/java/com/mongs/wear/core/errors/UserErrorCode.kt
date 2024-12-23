package com.mongs.wear.core.errors

enum class UserErrorCode(
    private val message: String,
) : ErrorCode {

    DATA_USER_GET_MAP_COLLECTIONS("컬렉션 맵 조회 실패했습니다."),
    DATA_USER_GET_MONG_COLLECTIONS("컬렉션 맵 조회 실패했습니다."),
    DATA_USER_CREATE_FEEDBACK("오류 신고 실패했습니다."),
    DATA_USER_UPDATE_PLAYER("플레이어 업데이트 실패했습니다."),

    DATA_USER_BUY_SLOT("슬롯 구매 실패했습니다."),
    DATA_USER_EXCHANGE_WALKING("걸음 수 환전 실패했습니다."),
    DATA_USER_CHARGE_STAR_POINT("스타포인트 충전 실패했습니다."),
    DATA_USER_EXCHANGE_STAR_POINT("스타포인트 환전 실패했습니다."),

    DATA_USER_GET_SLOT_COUNT("슬롯 개수 조회에 실패했습니다."),
    DATA_USER_GET_STAR_POINT("스타포인트 조회에 실패했습니다."),
    DATA_USER_GET_WALKING_COUNT("걸음 수 조회에 실패했습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}