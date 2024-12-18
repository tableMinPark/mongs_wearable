package com.mongs.wear.presentation.pages.main.walking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.player.usecase.ExchangeWalkingCountUseCase
import com.mongs.wear.domain.player.usecase.GetWalkingCountUseCase
import com.mongs.wear.domain.slot.usecase.GetNowSlotPayPointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainWalkingViewModel @Inject constructor(
    private val getNowSlotPayPointUseCase: GetNowSlotPayPointUseCase,
    private val getWalkingCountUseCase: GetWalkingCountUseCase,
    private val exchangeWalkingCountUseCase: ExchangeWalkingCountUseCase,
): ViewModel() {

    val uiState: UiState = UiState()

    val payPoint: LiveData<Int> get() = _payPoint
    private val _payPoint = MediatorLiveData<Int>()
    val walkingCount: LiveData<Int> get() = _walkingCount
    private val _walkingCount = MediatorLiveData<Int>()

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                uiState.loadingBar = true

                _payPoint.addSource(withContext(Dispatchers.IO) { getNowSlotPayPointUseCase() }) {
                    _payPoint.value = it
                }

                _walkingCount.addSource(withContext(Dispatchers.IO) { getWalkingCountUseCase() }) {
                    _walkingCount.value = it
                }

                uiState.loadingBar = false

            } catch (_: ErrorException) {

            }
        }
    }

    fun chargePayPoint(mongId: Long, walkingCount: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                uiState.chargePayPointDialog = false
                uiState.loadingBar = true
                exchangeWalkingCountUseCase(mongId = mongId, walkingCount = walkingCount)
                uiState.loadingBar = false
            } catch (_: ErrorException) {
                uiState.loadingBar = false
            }
        }
    }

    class UiState (
        loadingBar: Boolean = true,
        chargePayPointDialog: Boolean = false,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
        var chargePayPointDialog by mutableStateOf(chargePayPointDialog)
    }
}