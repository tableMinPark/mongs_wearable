package com.mongs.wear.core.errors

enum class UserErrorCode(
    private val message: String,
) : ErrorCode {

    DATA_USER_GET_MAP_COLLECTIONS("컬렉션 맵 조회 실패했습니다."),
    DATA_USER_GET_MONG_COLLECTIONS("컬렉션 맵 조회 실패했습니다."),
    DATA_USER_CREATE_FEEDBACK("오류 신고 실패했습니다."),
    DATA_USER_GET_PLAYER("플레이어 정보 조회 실패했습니다."),

    DATA_USER_BUY_SLOT("슬롯 구매 실패했습니다."),
    DATA_USER_EXCHANGE_WALKING("걸음 수 환전 실패했습니다."),
    DATA_USER_CHARGE_STAR_POINT("스타포인트 충전 실패했습니다."),
    DATA_USER_EXCHANGE_STAR_POINT("스타포인트 환전 실패했습니다."),

    DATA_USER_GET_SLOT_COUNT("슬롯 개수 조회에 실패했습니다."),
    DATA_USER_GET_STAR_POINT("스타포인트 조회에 실패했습니다."),
    DATA_USER_GET_WALKING_COUNT("걸음 수 조회에 실패했습니다."),

    DATA_USER_GET_PRODUCT_IDS("상품 목록 조회에 실패했습니다."),
    DATA_USER_CONSUME_PRODUCT_ORDER("상품 소비에 실패했습니다."),
    DATA_USER_CONSUMED_ORDER_IDS("상품 소비 ID 목록 조회에 실패했습니다."),

    PRESENTATION_USER_BILLING_CONNECT("인앱 결제 시스템 연결에 실패했습니다."),
    PRESENTATION_USER_BILLING_NOT_SUPPORT("결제를 지원하지 않는 기기입니다."),
    PRESENTATION_USER_GET_PRODUCTS("인앱 상품 조회에 실패했습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}