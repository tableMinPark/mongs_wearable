package com.paymong.wear.domain.usecase.member

import com.paymong.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class BuySlotUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke() {
        val maxSlot = memberRepository.buySlot()
        memberRepository.setMaxSlot(maxSlot = maxSlot)
    }
}