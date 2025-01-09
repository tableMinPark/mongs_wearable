package com.mongs.wear.domain.management.usecase

import android.util.Log
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.global.usecase.BaseParamUseCase
import com.mongs.wear.domain.management.exception.TrainingMongException
import com.mongs.wear.domain.management.repository.ManagementRepository
import com.mongs.wear.domain.management.repository.SlotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrainingMongUseCase @Inject constructor(
    private val slotRepository: SlotRepository,
    private val managementRepository: ManagementRepository,
) : BaseParamUseCase<TrainingMongUseCase.Param, Unit>() {

    override suspend fun execute(param: Param) {

        withContext(Dispatchers.IO) {
            Log.d("TrainingSlotUseCase", "invoke: ${param.trainingCode}, ${param.score}")
        }
    }

    data class Param(

        val trainingCode: String,

        val score: Int,
    )

    override fun handleException(exception: ErrorException) {
        super.handleException(exception)

        when(exception.code) {
            else -> throw TrainingMongException()
        }
    }
}