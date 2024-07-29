package com.mongs.wear.domain.usecase.feed

import com.mongs.wear.domain.code.FeedbackCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.repositroy.CodeRepository
import com.mongs.wear.domain.repositroy.FeedbackRepository
import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.FoodVo
import javax.inject.Inject

class GetFoodCodesUseCase @Inject constructor(
    private val codeRepository: CodeRepository,
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(): List<FoodVo> {
        try {
            val slotModel = slotRepository.getNowSlot()
            val foodVos = ArrayList<FoodVo>()
            val feedLogModelMap = managementRepository.getFeedLog(mongId = slotModel.mongId).associateBy { it.code }

            codeRepository.getFoodCodes().forEach {
                val feedLog = feedLogModelMap[it.code]!!

                foodVos.add(
                    FoodVo(
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

            return foodVos

        } catch (e: RepositoryException) {
            feedbackRepository.addFeedbackLog(
                groupCode = FeedbackCode.MANAGEMENT.groupCode,
                location = "GetFoodCodesUseCase",
                message = e.stackTrace.contentDeepToString(),
            )

            throw UseCaseException(e.errorCode, e)
        }
    }
}