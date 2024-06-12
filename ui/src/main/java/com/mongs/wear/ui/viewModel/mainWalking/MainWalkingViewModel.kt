package com.mongs.wear.ui.viewModel.mainWalking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.member.ExchangePayPointWalkingUseCase
import com.mongs.wear.domain.usecase.member.GetWalkingCountUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotPayPointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainWalkingViewModel @Inject constructor(
    private val getNowSlotPayPointUseCase: GetNowSlotPayPointUseCase,
    private val getWalkingCountUseCase: GetWalkingCountUseCase,
    private val exchangePayPointWalkingUseCase: ExchangePayPointWalkingUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var payPoint: LiveData<Int> = MutableLiveData()
    var walkingCount: LiveData<Int> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                payPoint = getNowSlotPayPointUseCase()
                walkingCount = getWalkingCountUseCase()
                uiState.loadingBar = false
            } catch (e: UseCaseException) {
                e.printStackTrace()
            }

        }
    }

    fun chargePayPoint(walkingCount: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                exchangePayPointWalkingUseCase(walkingCount = walkingCount)
                uiState.chargePayPointDialog = false
            } catch (_: UseCaseException) {
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