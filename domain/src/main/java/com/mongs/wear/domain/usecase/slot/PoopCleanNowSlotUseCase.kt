package com.mongs.wear.domain.usecase.slot

import com.mongs.wear.domain.repositroy.ManagementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class PoopCleanNowSlotUseCase @Inject constructor(
    private val managementRepository: ManagementRepository,
) {
    suspend operator fun invoke(mongId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            managementRepository.setIsPoopCleaning(mongId = mongId, isPoopCleaning = true)
            managementRepository.poopCleanMong(mongId = mongId)
            delay(2000)
            managementRepository.setIsPoopCleaning(mongId = mongId, isPoopCleaning = false)
        }
    }
}