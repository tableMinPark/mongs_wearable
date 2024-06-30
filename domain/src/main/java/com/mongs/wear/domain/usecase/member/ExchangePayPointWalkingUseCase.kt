package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class ExchangePayPointWalkingUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val memberRepository: MemberRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(mongId: Long, walkingCount: Int) {
        try {
            return memberRepository.exchangePayPointWalking(mongId = mongId, walkingCount = walkingCount)
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MEMBER.groupCode,
                location = "ExchangePayPointWalkingUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}