package com.mongs.wear.worker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mongs.wear.domain.player.usecase.SyncWalkingCountUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class StepsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncWalkingCountUseCase: SyncWalkingCountUseCase,
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "StepsWorker"
        const val WORKER_NAME = "MONGS_STEPS_SYNC_WORKER"
    }

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    override suspend fun doWork(): Result = try {
        listenAndStop()
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }

    /**
     * 일발성 걸음 수 센서 값 조회
     */
    private fun listenAndStop() {

        Log.i(TAG, "step sensor listen start.")

        sensorManager.registerListener(object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    sensorManager.unregisterListener(this)

                    CoroutineScope(Dispatchers.IO).launch {
                        val totalWalkingCount = event.values[0].toInt()
//                        val totalWalkingCount = SystemClock.uptimeMillis().toInt()      // TODO: 센서 값 변경
                        Log.i(TAG, "totalWalkingCount: $totalWalkingCount")
                        syncWalkingCountUseCase(totalWalkingCount = totalWalkingCount)

                        Log.i(TAG, "step sensor listen stop.")
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        }, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }
}
