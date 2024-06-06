package com.mongs.wear.ui.viewModel.mainInteraction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun sleep() {
        viewModelScope.launch (Dispatchers.IO) {
            Log.d("test", "sleep call")
//            sleepingNowSlotUseCase()
        }
    }
    fun poop() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("test", "poop call")
//            poopCleanNowSlotUseCase()
        }
    }


    class UiState () {}
}