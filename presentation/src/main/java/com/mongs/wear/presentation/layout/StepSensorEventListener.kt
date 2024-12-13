package com.mongs.wear.presentation.layout

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StepSensorEventListener @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val memberRepository: MemberRepository,
) : SensorEventListener {

    override fun onSensorChanged(event: SensorEvent?) {

        event?.let {
            CoroutineScope(Dispatchers.IO).launch {

                val nowStepCount = it.values[0].toInt()

                val rebootFlag = deviceRepository.getRebootFlag()

                if (rebootFlag) {
                    // 재부팅 된 경우
                    val walkingCount = memberRepository.getWalkingCount()
                    memberRepository.setStartStepCount(stepCount = -walkingCount)
                    memberRepository.setEndStepCount(stepCount = nowStepCount)
                    memberRepository.setWalkingCount(walkingCount = walkingCount + nowStepCount)
                    deviceRepository.setRebootFlag(rebootFlag = false)

                } else {
                    val startStepCount = memberRepository.getStartStepCount()
                    memberRepository.setWalkingCount(walkingCount =nowStepCount - startStepCount)
                    memberRepository.setEndStepCount(stepCount = nowStepCount)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}