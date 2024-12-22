package com.mongs.wear.presentation.pages.main.interaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.core.errors.ManagerErrorCode
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
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            sleepingMongUseCase(mongId = mongId)
            uiState.navMainSlotView = true
        }
    }

    fun poopClean(mongId: Long) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {
            poopCleanMongUseCase(mongId = mongId)
            uiState.navMainSlotView = true
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        loadingBar: Boolean = false,
        navMainSlotView: Boolean = false,
        alertSleepingFail: Boolean = false,
        alertPoopCleanFail: Boolean = false,
    ) : BaseUiState() {

        var loadingBar by mutableStateOf(loadingBar)
        var navMainSlotView by mutableStateOf(navMainSlotView)
        var alertSleepingFail by mutableStateOf(alertSleepingFail)
        var alertPoopCleanFail by mutableStateOf(alertPoopCleanFail)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                ManagerErrorCode.MANAGER_SLEEP_MONG -> {
                    uiState.alertSleepingFail = true
                }

                ManagerErrorCode.MANAGER_POOP_CLEAN_MONG -> {
                    uiState.alertPoopCleanFail = true
                }
            }
        }
    }
}