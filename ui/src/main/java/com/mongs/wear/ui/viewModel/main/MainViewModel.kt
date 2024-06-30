package com.mongs.wear.ui.viewModel.main

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.client.MqttBattleClient
import com.mongs.wear.domain.client.MqttEventClient
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.MemberRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.usecase.common.SetBuildVersionUseCase
import com.mongs.wear.domain.usecase.common.SetDeviceIdUseCase
import com.mongs.wear.domain.usecase.feedback.RemoveFeedbackLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val memberRepository: MemberRepository,
    private val slotRepository: SlotRepository,
    private val mqttEventClient: MqttEventClient,
    private val mqttBattleClient: MqttBattleClient,

    private val removeFeedbackLogUseCase: RemoveFeedbackLogUseCase,
    private val setBuildVersionUseCase: SetBuildVersionUseCase,
    private val setDeviceIdUseCase: SetDeviceIdUseCase,
) : ViewModel() {

    private lateinit var sensorManager: SensorManager
    private val stepSensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
//            val nowWalkingStep = event.values[0].toInt()
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    memberRepository.addWalkingCount(addWalkingCount = 1)
                } catch (_: RepositoryException) {
                }
            }
        }
    }

    val networkFlag: LiveData<Boolean> get() = _networkFlag
    private val _networkFlag = MediatorLiveData<Boolean>()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _networkFlag.addSource(
                withContext(Dispatchers.IO) {
                    deviceRepository.getNetworkFlagLive()
                }
            ) { networkFlag ->
                _networkFlag.value = networkFlag
            }
        }
    }

    fun init(buildVersion: String) {
        viewModelScope.launch(Dispatchers.IO)  {
            setBuildVersionUseCase(buildVersion = buildVersion)
            setDeviceIdUseCase()
            removeFeedbackLogUseCase()
        }
    }

    fun connectSensor(sensorManager: SensorManager) {
        try {
            this.sensorManager = sensorManager
            this.sensorManager.registerListener(
                stepSensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),      //TYPE_STEP_COUNTER  TYPE_GRAVITY
                SensorManager.SENSOR_DELAY_FASTEST
            )
        } catch (_: RuntimeException) {
        }
    }
    fun disconnectSensor() {
        runBlocking(Dispatchers.IO) {
            try {
                sensorManager.unregisterListener(stepSensorEventListener)
            } catch (_: RuntimeException) {
            }
        }
    }
    fun reconnectMqttEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mqttEventClient.reconnect(
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
            } catch (_: RuntimeException) {
            }
        }
    }
    fun pauseConnectMqttEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    mqttEventClient.pauseConnect()
                } catch (_: RuntimeException) {
                }
            }
        }
    }
    fun disconnectMqttEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                try {
                    mqttEventClient.disconnect()
                } catch (_: RuntimeException) {
                }
            }
        }
    }
    fun disconnectMqttBattle() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                try {
                    mqttBattleClient.disconnect()
                } catch (_: RuntimeException) {
                }
            }
        }
    }
}