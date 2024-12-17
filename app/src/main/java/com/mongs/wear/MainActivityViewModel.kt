package com.mongs.wear

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.AppRepository
import com.mongs.wear.presentation.layout.StepSensorEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val stepSensorEventListener: StepSensorEventListener,
) : ViewModel() {

    private lateinit var sensorManager: SensorManager

    fun init(network: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.setNetwork(network = network)
        }
    }

    fun connectSensor(sensorManager: SensorManager) = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.sensorManager = sensorManager
        //TYPE_STEP_DETECTOR TYPE_STEP_COUNTER TYPE_GRAVITY
        this@MainActivityViewModel.sensorManager.registerListener(
            this@MainActivityViewModel.stepSensorEventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun disconnectSensor() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.sensorManager.unregisterListener(this@MainActivityViewModel.stepSensorEventListener)
    }

    fun resumeConnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.resumeConnect()
    }

    fun pauseConnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        runBlocking { this@MainActivityViewModel.mqttClient.pauseConnect() }
    }

    fun disconnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        runBlocking { this@MainActivityViewModel.mqttClient.disconnect() }
    }
}