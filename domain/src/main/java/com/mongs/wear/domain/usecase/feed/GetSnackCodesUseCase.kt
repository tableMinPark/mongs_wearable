package com.mongs.wear.domain.usecase.feed

import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SnackVo
import javax.inject.Inject

class GetSnackCodesUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(): List<SnackVo> {
        val slotModel = slotRepository.getNowSlot()
        val snackVos = ArrayList<SnackVo>()
        val feedLogModelMap = managementRepository.getFeedLog(mongId = slotModel.mongId).associateBy { it.code }

        return snackVos
    }
}