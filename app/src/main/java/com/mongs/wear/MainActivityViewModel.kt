package com.mongs.wear

import android.os.SystemClock
import android.util.Log
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.domain.player.repository.PlayerRepository
import com.mongs.wear.presentation.common.BaseViewModel
import com.mongs.wear.presentation.common.StepSensorManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
    private val playerRepository: PlayerRepository,
    private val stepSensorManager: StepSensorManager,
) : BaseViewModel() {

    init {

        viewModelScopeWithHandler.launch {

            val bootTime = LocalDateTime.now()
                .minusSeconds(TimeUnit.MILLISECONDS.toSeconds(SystemClock.uptimeMillis()))

            appRepository.setBootTime(bootTime = bootTime)

            Log.d("test", bootTime.toString())
        }
    }

    fun setNetworkAvailable(network: Boolean) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            appRepository.setNetwork(network = network)
        }
    }

    fun connectSensor() = CoroutineScope(Dispatchers.IO).launch {
        stepSensorManager.listen()
    }

    fun disconnectSensor() = CoroutineScope(Dispatchers.IO).launch {
        stepSensorManager.stop()
    }

    fun resumeConnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.resumeConnect()
    }

    fun pauseConnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.pauseConnect()
    }

    fun disconnectMqtt() = CoroutineScope(Dispatchers.IO).launch {
        this@MainActivityViewModel.mqttClient.disconnect()
    }

    override fun exceptionHandler(exception: Throwable) {}
}