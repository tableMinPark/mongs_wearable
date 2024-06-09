package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class BuySlotUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke() {
        try {
            val maxSlot = memberRepository.buySlot()
            memberRepository.setMaxSlot(maxSlot = maxSlot)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}