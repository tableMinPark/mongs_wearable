package com.mongs.wear.ui.viewModel.mainActivity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val memberRepository: MemberRepository,
    private val slotRepository: SlotRepository,
    private val mqttClient: MqttClient,
) : ViewModel() {

    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensorEventListener: SensorEventListener

    fun initSensor(sensorManager: SensorManager) {
        try {
            this.sensorManager = sensorManager
            stepSensorEventListener = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent) {
                    val nowWalkingStep = event.values[0].toInt()
                    viewModelScope.launch(Dispatchers.IO) {
                        memberRepository.addWalkingCount(addWalkingCount = 1)
                    }
                }
            }
            sensorManager.registerListener(
                stepSensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),      //TYPE_STEP_COUNTER  TYPE_RELATIVE_HUMIDITY
                SensorManager.SENSOR_DELAY_FASTEST
            )
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    fun resetSensor() {
        runBlocking(Dispatchers.IO) {
            try {
                sensorManager.unregisterListener(stepSensorEventListener)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }

    fun initMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    mqttClient.resetConnection()
                } catch (e: RepositoryException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun reconnectMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mqttClient.reconnect(
                    resetMember = {
                        viewModelScope.launch(Dispatchers.IO) {
                            memberRepository.setMember()
                        }
                    },
                    resetSlot = {
                        viewModelScope.launch(Dispatchers.IO) {
                            slotRepository.syncNowSlot()
                        }
                    },
                )
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }

    fun disconnectMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    mqttClient.disconnect()
                } catch (e: RuntimeException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun initDeviceInfo(buildVersion: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                deviceRepository.setBuildVersion(buildVersion)
            } catch (e: RepositoryException) {
                e.printStackTrace()
            }
        }
    }
}