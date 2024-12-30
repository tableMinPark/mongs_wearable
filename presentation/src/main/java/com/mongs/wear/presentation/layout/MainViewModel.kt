package com.mongs.wear.presentation.layout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.errors.CommonErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.common.client.MqttClient
import com.mongs.wear.domain.common.repository.AppRepository
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
) : BaseViewModel() {

    val network: LiveData<Boolean> get() = _network
    private val _network = MediatorLiveData<Boolean>()

    init {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            uiState.loadingBar = true

            _network.addSource(withContext(Dispatchers.IO) { appRepository.getNetworkLive() }) {
                _network.value = it
            }

            mqttClient.connect()

            uiState.loadingBar = false
        }
    }

    val uiState = UiState()

    class UiState : BaseUiState() {
        var loadingBar by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {
                CommonErrorCode.DATA_COMMON_MQTT_CONNECT -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        appRepository.setNetwork(network = false)
                    }
                }
            }
        }
    }
}