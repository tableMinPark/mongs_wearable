package com.mongs.wear.presentation.pages.main.interaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.usecase.PoopCleanMongUseCase
import com.mongs.wear.domain.slot.usecase.SleepingMongUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainInteractionViewModel @Inject constructor(
    private val sleepingMongUseCase: SleepingMongUseCase,
    private val poopCleanMongUseCase: PoopCleanMongUseCase,
): BaseViewModel() {

    fun sleeping(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                sleepingMongUseCase(mongId = mongId)
                uiState.navMainSlotView = true
            } catch (_: ErrorException) {
                uiState.alertSleepingFail = true
            }
        }
    }

    fun poopClean(mongId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                poopCleanMongUseCase(mongId = mongId)
                uiState.navMainSlotView = true
            } catch (_: ErrorException) {
                uiState.alertPoopCleanFail = true
            }
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        navMainSlotView: Boolean = false,
        alertSleepingFail: Boolean = false,
        alertPoopCleanFail: Boolean = false,
    ) : BaseUiState() {

        var navMainSlotView by mutableStateOf(navMainSlotView)
        var alertSleepingFail by mutableStateOf(alertSleepingFail)
        var alertPoopCleanFail by mutableStateOf(alertPoopCleanFail)
    }

    override fun exceptionHandler(exception: Throwable, loadingBar: Boolean, errorToast: Boolean) {

        uiState.loadingBar = loadingBar
        uiState.errorToast = errorToast

        if (exception is ErrorException) {

            when ()

            uiState.alertSleepingFail = true

            uiState.alertPoopCleanFail = true
        }
    }
}