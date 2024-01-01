package com.paymong.wear.domain.viewModel.code

enum class SlotCode(
    val message: String
) {
    STAND_BY("준비"),
    LOAD_MONG("몽 로딩 중"),
    GENERATE_MONG("몽 생성 중"),
    END("끝")
}