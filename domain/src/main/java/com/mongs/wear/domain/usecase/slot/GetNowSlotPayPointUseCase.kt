package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.SlotRepository
import javax.inject.Inject

class GetNowSlotPayPointUseCase @Inject constructor(
    private val slotRepository: SlotRepository
) {
    suspend operator fun invoke(): LiveData<Int> {
        return try {
            val slotModel = slotRepository.getNowSlotLive()
            slotModel.map { it.payPoint }
        } catch (e: RepositoryException) {
            MutableLiveData(0)
        }
    }
}