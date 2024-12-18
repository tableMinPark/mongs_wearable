package com.mongs.wear.presentation.pages.main.interaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.usecase.PoopCleanNowSlotUseCase
import com.mongs.wear.domain.slot.usecase.SleepingNowSlotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainInteractionViewModel @Inject constructor(
    private val sleepingNowSlotUseCase: SleepingNowSlotUseCase,
    private val poopCleanNowSlotUseCase: PoopCleanNowSlotUseCase,
): ViewModel() {

    val uiState: UiState = UiState()

    fun sleeping(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                sleepingNowSlotUseCase(mongId = mongId)
                uiState.navMainSlotView = true
            } catch (_: ErrorException) {
                uiState.alertSleepingFail = true
            }
        }
    }

    fun poopClean(mongId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                poopCleanNowSlotUseCase(mongId = mongId)
                uiState.navMainSlotView = true
            } catch (_: ErrorException) {
                uiState.alertPoopCleanFail = true
            }
        }
    }

    class UiState (
        navMainSlotView: Boolean = false,
        alertSleepingFail: Boolean = false,
        alertPoopCleanFail: Boolean = false,
    ) {
        var navMainSlotView by mutableStateOf(navMainSlotView)
        var alertSleepingFail by mutableStateOf(alertSleepingFail)
        var alertPoopCleanFail by mutableStateOf(alertPoopCleanFail)
    }
}