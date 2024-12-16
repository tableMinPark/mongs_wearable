package com.mongs.wear.presentation.layout

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StepSensorEventListener @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val playerRepository: PlayerRepository,
) : SensorEventListener {

    override fun onSensorChanged(event: SensorEvent?) {

        event?.let {
            CoroutineScope(Dispatchers.IO).launch {

                val nowStepCount = it.values[0].toInt()

                val rebootFlag = deviceRepository.getRebootFlag()

                if (rebootFlag) {
                    // 재부팅 된 경우
                    val walkingCount = playerRepository.getWalkingCount()
                    playerRepository.setStartStepCount(stepCount = -walkingCount)
                    playerRepository.setEndStepCount(stepCount = nowStepCount)
                    playerRepository.setWalkingCount(walkingCount = walkingCount + nowStepCount)
                    deviceRepository.setRebootFlag(rebootFlag = false)

                } else {
                    val startStepCount = playerRepository.getStartStepCount()
                    playerRepository.setWalkingCount(walkingCount =nowStepCount - startStepCount)
                    playerRepository.setEndStepCount(stepCount = nowStepCount)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}