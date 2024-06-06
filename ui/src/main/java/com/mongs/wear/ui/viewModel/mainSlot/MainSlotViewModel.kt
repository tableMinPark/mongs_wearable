package com.mongs.wear.ui.viewModel.mainSlot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.usecase.slot.EvoluteNowSlotUseCase
import com.mongs.wear.domain.usecase.slot.StrokeNowSlotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSlotViewModel @Inject constructor(
    private val strokeNowSlotUseCase: StrokeNowSlotUseCase,
    private val evoluteNowSlotUseCase: EvoluteNowSlotUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    fun stroke() {
        viewModelScope.launch (Dispatchers.IO) {
            strokeNowSlotUseCase()
        }
    }

    fun evolution() {
        viewModelScope.launch (Dispatchers.IO) {
            evoluteNowSlotUseCase()
        }
    }

    fun graduationCheck() {
        viewModelScope.launch (Dispatchers.IO) {

        }
    }

    class UiState (
        isEvolution: Boolean = false,
        isGraduation: Boolean = false,
    ) {
        var isEvolution by mutableStateOf(isEvolution)
        var isGraduation by mutableStateOf(isGraduation)
    }
}