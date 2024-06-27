package com.mongs.wear.ui.viewModel.mainInteraction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.slot.PoopCleanNowSlotUseCase
import com.mongs.wear.domain.usecase.slot.SleepingNowSlotUseCase
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

    fun sleeping() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                sleepingNowSlotUseCase()
                uiState.navMainSlotView = true
            } catch (_: UseCaseException) {
                uiState.alertSleepingFail = true
            }
        }
    }
    fun poopClean() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                poopCleanNowSlotUseCase()
                uiState.navMainSlotView = true
            } catch (_: UseCaseException) {
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