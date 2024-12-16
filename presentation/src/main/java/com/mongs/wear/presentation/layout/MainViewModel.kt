package com.mongs.wear.presentation.layout

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.client.BattleMqttClient
import com.mongs.wear.domain.client.ManagementMqttClient
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.AppRepository
import com.mongs.wear.domain.repositroy.DeviceRepository
import com.mongs.wear.domain.repositroy.PlayerRepository
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.usecase.common.SetBuildVersionUseCase
import com.mongs.wear.domain.usecase.common.SetDeviceIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val slotRepository: SlotRepository,
    private val managementMqttClient: ManagementMqttClient,
    private val battleMqttClient: BattleMqttClient,

    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val stepSensorEventListener: StepSensorEventListener,

) : ViewModel() {

//    companion object {
//        private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
//    }

    val uiState = UiState()

    private lateinit var sensorManager: SensorManager

    val networkFlag: LiveData<Boolean> get() = _networkFlag
    private val _networkFlag = MediatorLiveData<Boolean>()

    fun init(buildVersion: String, isNetworkAvailable: Boolean) {

        viewModelScope.launch(Dispatchers.Main) {

            if (isNetworkAvailable) {

                mqttClient.setConnection()
            }

            withContext(Dispatchers.IO) {
                appRepository.setNetwork(network = isNetworkAvailable)
                appRepository.set
            }

            _networkFlag.addSource(withContext(Dispatchers.IO) { appRepository.getNetworkLive() }) {
                _networkFlag.value = it
            }

//            val upTime = deviceRepository.getUpTime()
//            val upTimeInt = upTime.format(dateTimeFormatter).toLong()
//            val nowUpTimeInt = nowUpTime.format(dateTimeFormatter).toLong()
//
//            if (upTimeInt < nowUpTimeInt - 5 || nowUpTimeInt + 5 < upTimeInt) {
//                deviceRepository.setRebootFlag(rebootFlag = true)
//                deviceRepository.setUpTime(upTime = nowUpTime)
//            }

            uiState.loadingBar = false
        }
    }

    fun connectSensor(sensorManager: SensorManager) = CoroutineScope(Dispatchers.IO).launch {
        this@MainViewModel.sensorManager = sensorManager
        //TYPE_STEP_DETECTOR TYPE_STEP_COUNTER TYPE_GRAVITY
        this@MainViewModel.sensorManager.registerListener(this@MainViewModel.stepSensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun disconnectSensor() = CoroutineScope(Dispatchers.IO).launch {
        this@MainViewModel.sensorManager.unregisterListener(this@MainViewModel.stepSensorEventListener)
    }

    fun reconnectMqttEvent() = CoroutineScope(Dispatchers.IO).launch {
        managementMqttClient.reconnect(
            resetMember = {
                viewModelScope.launch(Dispatchers.IO) {
                    playerRepository.setMember()
                }
            },
            resetSlot = {
                viewModelScope.launch(Dispatchers.IO) {
                    slotRepository.syncNowSlot()
                }
            },
        )
    }

    fun pauseConnectMqttEvent() = CoroutineScope(Dispatchers.IO).launch {
        runBlocking { managementMqttClient.pauseConnect() }
    }

    fun disconnectMqttEvent() = CoroutineScope(Dispatchers.IO).launch {
        runBlocking { managementMqttClient.disconnect() }
    }

    fun disconnectMqttBattle() = CoroutineScope(Dispatchers.IO).launch {
        runBlocking { battleMqttClient.disconnect() }
    }

    class UiState (
        loadingBar: Boolean = false,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
    }
}