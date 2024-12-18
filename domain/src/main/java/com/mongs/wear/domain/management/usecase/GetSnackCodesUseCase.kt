package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.slot.repository.SlotRepository
import com.mongs.wear.domain.vo.SnackVo
import javax.inject.Inject

class GetSnackCodesUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(): List<SnackVo> {
        val slotModel = slotRepository.getNowSlot()
        val snackVos = ArrayList<SnackVo>()
        val feedLogModelMap = managementRepository.getFeedItems(mongId = slotModel.mongId).associateBy { it.foodTypeCode }

        return snackVos
    }
}