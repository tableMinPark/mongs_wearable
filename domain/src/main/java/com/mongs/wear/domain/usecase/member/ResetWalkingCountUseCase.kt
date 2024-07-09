package com.mongs.wear.domain.usecase.member

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class ResetWalkingCountUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke() {
        try {
            val endStepCount = memberRepository.getEndStepCount()
            memberRepository.setStartStepCount(stepCount = endStepCount)
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MEMBER.groupCode,
                location = "ResetWalkingCountUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}