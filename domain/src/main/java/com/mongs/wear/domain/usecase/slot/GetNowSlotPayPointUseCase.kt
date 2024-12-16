package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class GetNowSlotPayPointUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
) {
    suspend operator fun invoke(): LiveData<Int> {
        return slotRepository.getNowSlotLive().map {
            it?.payPoint ?: run {
                0
            }
        }
    }
}