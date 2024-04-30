package com.paymong.wear.domain.error

enum class CommonErrorCode(val message: String) : ErrorCode {
    MUST_UPDATE_APP("앱 업데이트가 필요합니다."),
    VERSION_CHECK_FAIL("버전 확인 실패"),
    INIT_CODE_FAIL("코드 값 초기화 실패"),
    GET_MEMBER_FAIL("회원 정보 조회 실패 (MAX_SLOT, STAR_POINT)"),
    GET_FOOD_CODE_BY_GROUP_CODE_FAIL("음식 코드 값 조회 실패"),
    ;
    override fun message(): String {
        return message
    }
}