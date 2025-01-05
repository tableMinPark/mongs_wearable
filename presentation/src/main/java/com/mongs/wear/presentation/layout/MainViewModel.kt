package com.mongs.wear.presentation.layout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mongs.wear.domain.device.exception.ConnectMqttException
import com.mongs.wear.domain.device.usecase.ConnectMqttUseCase
import com.mongs.wear.domain.device.usecase.GetNetworkUseCase
import com.mongs.wear.domain.device.usecase.SetNetworkUseCase
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setNetworkUseCase: SetNetworkUseCase,
    private val getNetworkUseCase: GetNetworkUseCase,
    private val connectMqttUseCase: ConnectMqttUseCase,
) : BaseViewModel() {

    val network: LiveData<Boolean> get() = _network
    private val _network = MediatorLiveData(true)

    init {
        viewModelScopeWithHandler.launch(Dispatchers.Main) {

            uiState.loadingBar = true

            _network.addSource(withContext(Dispatchers.IO) { getNetworkUseCase() }) {
                _network.value = it
            }

            connectMqttUseCase()

            uiState.loadingBar = false
        }
    }

    val uiState = UiState()

    class UiState : BaseUiState() {}

    override fun exceptionHandler(exception: Throwable) {

        when (exception) {
            is ConnectMqttException -> {
                CoroutineScope(Dispatchers.IO).launch {
                    setNetworkUseCase(false)
                }
            }

            else -> {
                uiState.loadingBar = false
            }
        }
    }
}