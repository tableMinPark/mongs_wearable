package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.MemberRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class ExchangePayPointWalkingUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(walkingCount: Int) {
        try {
            val slotModel = slotRepository.getNowSlot()
            return memberRepository.exchangePayPointWalking(mongId = slotModel.mongId, walkingCount = walkingCount)
        } catch (e: RepositoryException) {
            throw UseCaseException(e.errorCode)
        }
    }
}