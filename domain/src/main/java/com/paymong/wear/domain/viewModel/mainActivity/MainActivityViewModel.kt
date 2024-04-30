package com.paymong.wear.domain.viewModel.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.common.DeviceRepository
import com.paymong.wear.domain.repository.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    fun initMqtt() {
        // mqtt 설정 초기화
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.resetNotification()
        }
    }

    fun reconnectMqtt() {
        // 로그인 되어 있으면 재연결
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.reconnectNotification()
        }
    }

    fun disconnectMqtt() {
        // 로그인 되어 있으면 연결 해제
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.disconnectNotification()
        }
    }

    fun updateBuildVersion(buildVersion: String) {
        viewModelScope.launch(Dispatchers.Main) {
            deviceRepository.setBuildVersion(buildVersion)
        }
    }
}