package com.mongs.wear.domain.slot.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainSlotErrorCode(
    private val message: String,
) : ErrorCode {

    DOMAIN_CREATE_MONG_FAIL("몽 생성에 실패했습니다."),
    DOMAIN_DELETE_MONG_FAIL("몽 삭제에 실패했습니다."),
    DOMAIN_EVOLUTION_MONG_FAIL("몽 진화에 실패했습니다."),
    DOMAIN_GET_CURRENT_SLOT_FAIL("현재 슬롯 조회에 실패했습니다."),
    DOMAIN_GRADUATE_CHECK_MONG_FAIL("졸업 체크에 실패했습니다."),
    DOMAIN_GRADUATE_MONG_FAIL("몽 졸업에 실패했습니다."),
    DOMAIN_POOP_CLEAN_MONG_FAIL("배변 처리에 실패했습니다."),
    DOMAIN_SET_CURRENT_SLOT_FAIL("현재 슬롯 설정에 실패했습니다."),
    DOMAIN_SLEEPING_MONG_FAIL("몽 수면에 실패했습니다."),
    DOMAIN_STROKE_MONG_FAIL("몽 쓰다듬기에 실패했습니다."),
    DOMAIN_TRAINING_MONG_FAIL("몽 훈련 완료에 실패했습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}