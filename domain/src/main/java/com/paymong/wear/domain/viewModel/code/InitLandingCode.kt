package com.paymong.wear.domain.viewModel.code

enum class InitLandingCode(
    val message: String
) {
    STAND_BY("준비"),
    SIGN_IN_CHECK("구글 로그인 여부 확인 중"),
    SIGN_IN_INIT("구글 로그인 화면"),
    SIGN_IN_PROCESS_GOOGLE("구글 로그인 시도 중"),
    SIGN_IN_PROCESS("페이몽 로그인 시도 중"),
    SIGN_IN_SUCCESS("로그인 성공"),
    NAVIGATE("끝")
}
