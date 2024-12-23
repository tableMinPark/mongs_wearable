package com.mongs.wear.presentation.pages.main.walking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mongs.wear.core.errors.SlotErrorCode
import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.player.usecase.ExchangeWalkingCountUseCase
import com.mongs.wear.domain.player.usecase.GetStepsUseCase
import com.mongs.wear.domain.slot.usecase.GetCurrentSlotUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainWalkingViewModel @Inject constructor(
    private val getCurrentSlotUseCase: GetCurrentSlotUseCase,
    private val getStepsUseCase: GetStepsUseCase,
    private val exchangeWalkingCountUseCase: ExchangeWalkingCountUseCase,
): BaseViewModel() {

    private val _payPoint = MediatorLiveData<Int>()
    val payPoint: LiveData<Int> get() = _payPoint

    private val _walkingCount = MediatorLiveData<Int>()
    val walkingCount: LiveData<Int> get() = _walkingCount

    init {
        viewModelScopeWithHandler.launch (Dispatchers.Main) {

            uiState.loadingBar = true

            _payPoint.addSource(withContext(Dispatchers.IO) { getCurrentSlotUseCase() }) {
                it?.let { slotVo ->
                    _payPoint.value = slotVo.payPoint
                } ?: run { _payPoint.value = 0 }
            }

            _walkingCount.addSource(withContext(Dispatchers.IO) { getStepsUseCase() }) { walkingCount ->
                _walkingCount.value = walkingCount
            }

            uiState.loadingBar = false
        }
    }

    fun chargePayPoint(mongId: Long, walkingCount: Int) {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {

            uiState.loadingBar = true
            uiState.chargePayPointDialog = false

            exchangeWalkingCountUseCase(mongId = mongId, walkingCount = walkingCount)

            uiState.loadingBar = false
        }
    }

    val uiState: UiState = UiState()

    class UiState : BaseUiState() {

        var loadingBar by mutableStateOf(false)
        var chargePayPointDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                SlotErrorCode.DATA_SLOT_GET_CURRENT_SLOT -> {}

                UserErrorCode.DATA_USER_GET_WALKING_COUNT -> {}

                UserErrorCode.DATA_USER_EXCHANGE_WALKING -> {}
            }
        }
    }
}