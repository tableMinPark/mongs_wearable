package com.mongs.wear.presentation.pages.store.exchangePayPoint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mongs.wear.domain.management.exception.GetCurrentSlotException
import com.mongs.wear.domain.management.usecase.GetCurrentSlotUseCase
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.domain.player.exception.ExchangeWalkingCountException
import com.mongs.wear.domain.player.exception.GetStepsException
import com.mongs.wear.domain.player.usecase.ExchangeWalkingCountUseCase
import com.mongs.wear.domain.player.usecase.GetStepsUseCase
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StoreExchangePayPointViewModel @Inject constructor(
    private val getCurrentSlotUseCase: GetCurrentSlotUseCase,
    private val getStepsUseCase: GetStepsUseCase,
    private val exchangeWalkingCountUseCase: ExchangeWalkingCountUseCase,
): BaseViewModel() {

    private val _mongVo = MediatorLiveData<MongVo?>(null)
    val mongVo: LiveData<MongVo?> get() = _mongVo

    private val _walkingCount = MediatorLiveData<Int>()
    val walkingCount: LiveData<Int> get() = _walkingCount

    init {
        viewModelScopeWithHandler.launch (Dispatchers.Main) {

            uiState.loadingBar = true

            _mongVo.addSource(withContext(Dispatchers.IO) { getCurrentSlotUseCase() }) {
                it?.let { mongVo ->
                    _mongVo.value = mongVo
                }
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

            exchangeWalkingCountUseCase(
                ExchangeWalkingCountUseCase.Param(
                    mongId = mongId, walkingCount = walkingCount
                )
            )

            uiState.loadingBar = false
        }
    }

    val uiState: UiState = UiState()

    class UiState : BaseUiState() {
        var chargePayPointDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        when(exception) {
            is GetStepsException -> {
                uiState.loadingBar = false
            }

            is GetCurrentSlotException -> {
                uiState.loadingBar = false
            }

            is ExchangeWalkingCountException -> {
                uiState.loadingBar = false
                uiState.chargePayPointDialog = false
            }

            else -> {
                uiState.loadingBar = false
            }
        }
    }
}