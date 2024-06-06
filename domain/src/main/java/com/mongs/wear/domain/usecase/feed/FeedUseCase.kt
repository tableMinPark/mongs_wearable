package com.mongs.wear.domain.usecase.feed

import com.mongs.wear.domain.repositroy.ManagementRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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