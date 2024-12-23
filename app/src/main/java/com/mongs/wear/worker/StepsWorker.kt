package com.mongs.wear.worker

import android.content.Context
import android.os.SystemClock
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mongs.wear.domain.player.usecase.SyncWalkingCountUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class StepsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncWalkingCountUseCase: SyncWalkingCountUseCase,
    private val stepSensorManager: StepSensorManager,
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val WORKER_NAME = "MONGS_STEPS_SYNC_WORKER"
    }

    override suspend fun doWork(): Result {

        stepSensorManager.listen()

        val totalWalkingCount = SystemClock.uptimeMillis().toInt()

        val success = syncWalkingCountUseCase(totalWalkingCount = totalWalkingCount)

        stepSensorManager.stop()

        return if (success) Result.success() else Result.failure()
    }
}
