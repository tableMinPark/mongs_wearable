package com.paymong.wear.ui.viewModel.mainInteraction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.usecase.slot.PoopCleanNowSlotUseCase
import com.paymong.wear.domain.usecase.slot.SleepingNowSlotUseCase
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

    fun sleep() {
        viewModelScope.launch (Dispatchers.IO) {
            sleepingNowSlotUseCase()
        }
    }
    fun poop() {
        viewModelScope.launch(Dispatchers.IO) {
            poopCleanNowSlotUseCase()
        }
    }


    class UiState (
        isFeedActive: Boolean = false,
        isCollectionActive: Boolean = false,
        isSleepingActive: Boolean = false,
        isSlotPickActive: Boolean = false,
        isPoopCleanActive: Boolean = false,
        isTrainingActive: Boolean = false,
        isBattleActive: Boolean = false,
    ) {
        var isFeedActive by mutableStateOf(isFeedActive)
        var isCollectionActive by mutableStateOf(isCollectionActive)
        var isSleepingActive by mutableStateOf(isSleepingActive)
        var isSlotPickActive by mutableStateOf(isSlotPickActive)
        var isPoopCleanActive by mutableStateOf(isPoopCleanActive)
        var isTrainingActive by mutableStateOf(isTrainingActive)
        var isBattleActive by mutableStateOf(isBattleActive)
    }
}