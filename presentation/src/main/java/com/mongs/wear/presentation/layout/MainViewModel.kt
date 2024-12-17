package com.mongs.wear.presentation.layout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.client.MqttClient
import com.mongs.wear.domain.repositroy.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mqttClient: MqttClient,
    private val appRepository: AppRepository,
) : ViewModel() {

    val uiState = UiState()

    val network: LiveData<Boolean> get() = _network
    private val _network = MediatorLiveData<Boolean>()

    fun init() {

        viewModelScope.launch(Dispatchers.Main) {

            _network.addSource(withContext(Dispatchers.IO) { appRepository.getNetworkLive() }) { _network.value = it }

            withContext(Dispatchers.IO) {
                mqttClient.connect()
                uiState.loadingBar = false
            }
        }
    }

    class UiState (
        loadingBar: Boolean = true,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
    }
}