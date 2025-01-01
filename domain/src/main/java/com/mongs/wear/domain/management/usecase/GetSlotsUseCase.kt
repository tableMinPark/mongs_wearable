package com.mongs.wear.domain.management.usecase

import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.vo.SlotVo
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(): List<SlotVo> = managementRepository.getMongs()
        .map { mongModel ->
            SlotVo(
                code = SlotVo.SlotCode.EXISTS,
                mongVo = mongModel.toMongVo()
            )
        }
}