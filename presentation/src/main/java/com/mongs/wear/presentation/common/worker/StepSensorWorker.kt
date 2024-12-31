package com.mongs.wear.presentation.common.worker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mongs.wear.domain.player.usecase.SyncTotalWalkingCountUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@HiltWorker
class StepSensorWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncTotalWalkingCountUseCase: SyncTotalWalkingCountUseCase,
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "StepsWorker"
        const val WORKER_NAME = "MONGS_STEPS_SYNC_WORKER"
    }

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    override suspend fun doWork(): Result = try {

        val totalWalkingCount = this.getWalkingCount()

        Log.i(TAG, "totalWalkingCount: $totalWalkingCount")

        syncTotalWalkingCountUseCase(totalWalkingCount = totalWalkingCount)

        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }

    private suspend fun getWalkingCount(): Int {
        return suspendCancellableCoroutine { cont ->
            sensorManager.registerListener(object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        sensorManager.unregisterListener(this)
                        cont.resume(event.values[0].toInt())
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            }, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
}
