package com.mongs.wear.worker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.mongs.wear.domain.player.repository.PlayerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StepSensorManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val playerRepository: PlayerRepository
) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
    //TYPE_STEP_DETECTOR TYPE_STEP_COUNTER TYPE_GRAVITY

    private val stepListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                CoroutineScope(Dispatchers.IO).launch {

                    val nowStepCount = event.values[0].toInt()

                    Log.i("StepSensorManager", "nowStepCount: $nowStepCount")

//                    playerRepository.resetWalkingCount(walkingCount = nowStepCount)

                    // TODO: 걸음수에 대한 로직 구현 필요
//                if (rebootFlag) {
//                    // 재부팅 된 경우
//                    val walkingCount = playerRepository.getWalkingCount()
//                    playerRepository.setStartStepCount(stepCount = -walkingCount)
//                    playerRepository.setEndStepCount(stepCount = nowStepCount)
//                    playerRepository.setWalkingCount(walkingCount = walkingCount + nowStepCount)
//
//                } else {
//                    val startStepCount = playerRepository.getStartStepCount()
//                    playerRepository.setWalkingCount(walkingCount =nowStepCount - startStepCount)
//                    playerRepository.setEndStepCount(stepCount = nowStepCount)
//                }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    fun listen() {
        stepSensor?.let {
            sensorManager.registerListener(stepListener, it, SensorManager.SENSOR_DELAY_NORMAL)

            Log.i("StepSensorManager", "listen start")
        }
    }

    fun stop() {
        sensorManager.unregisterListener(stepListener)
        Log.i("StepSensorManager", "listen stop")
    }
}