package com.paymong.wear.domain.usecase.feed

import com.paymong.wear.domain.repositroy.ManagementRepository
import com.paymong.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class FeedUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository
) {
    suspend operator fun invoke(code: String) {
        val slotModel = slotRepository.getNowSlot()
        val mongId = slotModel.mongId

        managementRepository.feed(mongId = mongId, code = code)
    }
}