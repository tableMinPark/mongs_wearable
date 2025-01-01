package com.mongs.wear.domain.management.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.management.repository.SlotRepository
import com.mongs.wear.domain.management.vo.MongVo
import javax.inject.Inject

class GetCurrentSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<MongVo?> = slotRepository.getCurrentSlotLive().map {
        mongModel -> mongModel?.toMongVo()
    }
}