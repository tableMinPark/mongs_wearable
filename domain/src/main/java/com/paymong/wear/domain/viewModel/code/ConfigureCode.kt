package com.paymong.wear.domain.viewModel.code

enum class ConfigureCode(
    val message: String
) {
    STAND_BY(""),

    SIGN_OUT_PROCESS("로그아웃 시도..."),
    SIGN_OUT_SUCCESS("로그아웃 성공!"),
    SIGN_OUT_FAIL("로그아웃 실패!"),
    NAVIGATE("로그아웃 종료")
}
