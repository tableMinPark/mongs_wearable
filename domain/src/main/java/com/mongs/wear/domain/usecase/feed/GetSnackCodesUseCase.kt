package com.mongs.wear.domain.usecase.feed

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.CodeRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SnackVo
import javax.inject.Inject

class GetSnackCodesUseCase @Inject constructor(
    private val codeRepository: CodeRepository,
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    suspend operator fun invoke(): List<SnackVo> {
        try {
            val slotModel = slotRepository.getNowSlot()
            val snackVos = ArrayList<SnackVo>()
            val feedLogModelMap = managementRepository.getFeedLog(mongId = slotModel.mongId).associateBy { it.code }
            codeRepository.getSnackCodes().forEach {
                val feedLog = feedLogModelMap[it.code]!!

                snackVos.add(
                    SnackVo(
                        code = it.code,
                        name = it.name,
                        price = it.price,
                        addWeight = it.addWeight,
                        addStrength = it.addStrength,
                        addSatiety = it.addSatiety,
                        addHealthy = it.addHealthy,
                        addSleep = it.addSleep,
                        isCanBuy = feedLog.isCanBuy
                    )
                )
            }

            return snackVos

        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MANAGEMENT.groupCode,
                location = "GetSnackCodesUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}