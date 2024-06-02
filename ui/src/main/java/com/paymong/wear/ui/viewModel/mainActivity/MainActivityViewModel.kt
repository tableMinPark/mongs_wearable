package com.paymong.wear.ui.viewModel.mainActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.client.MqttClient
import com.paymong.wear.domain.repositroy.DeviceRepository
import com.paymong.wear.domain.usecase.configure.SetBackgroundMapCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mqttClient: MqttClient,
) : ViewModel() {
    fun initMqtt() {
        // mqtt 설정 초기화
        viewModelScope.launch(Dispatchers.IO) {
            mqttClient.resetConnection()
        }
    }

    fun reconnectMqtt() {
        // 로그인 되어 있으면 재연결
        viewModelScope.launch(Dispatchers.IO) {
            mqttClient.reconnect()
        }
    }

    fun disconnectMqtt() {
        // 로그인 되어 있으면 연결 해제
        viewModelScope.launch(Dispatchers.IO) {
            mqttClient.disconnect()
        }
    }

    fun initDeviceInfo(buildVersion: String) {
        viewModelScope.launch(Dispatchers.Main) {
            deviceRepository.setBuildVersion(buildVersion)
        }
    }
}