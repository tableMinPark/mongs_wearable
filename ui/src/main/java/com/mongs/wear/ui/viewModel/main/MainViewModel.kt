package com.mongs.wear.ui.viewModel.main

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
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
    val uiState = UiState()

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

    fun init(buildVersion: String) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                setBuildVersionUseCase(buildVersion = buildVersion)
                setDeviceIdUseCase()
                removeFeedbackLogUseCase()
            }

            _networkFlag.addSource(
                withContext(Dispatchers.IO) {
                    deviceRepository.getNetworkFlagLive()
                }
            ) { networkFlag ->
                _networkFlag.value = networkFlag
            }

            uiState.loadingBar = false
        }
    }

    fun connectSensor(sensorManager: SensorManager) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                this@MainViewModel.sensorManager = sensorManager
                this@MainViewModel.sensorManager.registerListener(
                    this@MainViewModel.stepSensorEventListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),      //TYPE_STEP_COUNTER  TYPE_GRAVITY
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            } catch (_: RuntimeException) {
            }
        }
    }
    fun disconnectSensor() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                this@MainViewModel.sensorManager.unregisterListener(this@MainViewModel.stepSensorEventListener)
            } catch (_: RuntimeException) {
            }
        }
    }
    fun reconnectMqttEvent() {
        CoroutineScope(Dispatchers.IO).launch {
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
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                try {
                    mqttEventClient.pauseConnect()
                } catch (_: RuntimeException) {
                }
            }
        }
    }
    fun disconnectMqttEvent() {
        CoroutineScope(Dispatchers.IO).launch {
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

    class UiState (
        loadingBar: Boolean = true,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
    }
}