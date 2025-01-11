package com.mongs.wear.core.errors

enum class PresentationErrorCode(
    private val message: String,
    private val isMessageShow: Boolean,
) : ErrorCode {

    // Global
    PRESENTATION_USER_BILLING_CONNECT("인앱 결제 시스템 연결 실패", false),
    PRESENTATION_USER_BILLING_NOT_SUPPORT("결제 지원하지 않는 기기", false),
    PRESENTATION_USER_GET_PRODUCTS("인앱 상품 조회 실패", false),
    ;

    override fun getMessage(): String {
        return this.message
    }

    override fun isMessageShow(): Boolean {
        return this.isMessageShow
    }
}