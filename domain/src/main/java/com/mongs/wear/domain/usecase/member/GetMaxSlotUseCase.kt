package com.mongs.wear.domain.usecase.member

import androidx.lifecycle.LiveData
import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import javax.inject.Inject

class GetMaxSlotUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(): LiveData<Int> {
        try {
            return memberRepository.getMaxSlotLive()
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MEMBER.groupCode,
                location = "GetMaxSlotUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}