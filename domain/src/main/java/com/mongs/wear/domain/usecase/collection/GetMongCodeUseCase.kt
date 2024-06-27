package com.mongs.wear.domain.usecase.collection

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.CodeRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.vo.MongVo
import javax.inject.Inject

class GetMongCodeUseCase @Inject constructor(
    private val codeRepository: CodeRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(code: String): MongVo {
        try {
            val mongCodeModel = codeRepository.getMongCode(code = code)
            return MongVo(code = mongCodeModel.code, name = mongCodeModel.name)
        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.BATTLE.groupCode,
                location = "GetMongCodeUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}