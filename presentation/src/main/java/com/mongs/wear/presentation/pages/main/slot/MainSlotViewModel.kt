package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.usecase.EvoluteNowSlotUseCase
import com.mongs.wear.domain.slot.usecase.GraduateReadySlotUseCase
import com.mongs.wear.domain.slot.usecase.StrokeNowSlotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSlotViewModel @Inject constructor(
    private val strokeNowSlotUseCase: StrokeNowSlotUseCase,
    private val evoluteNowSlotUseCase: EvoluteNowSlotUseCase,
    private val graduateReadySlotUseCase: GraduateReadySlotUseCase,
): ViewModel() {

    val uiState: UiState = UiState()

    fun stroke(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                strokeNowSlotUseCase(mongId = mongId)
            } catch (_: ErrorException) {
            }
        }
    }

    fun evolution(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                evoluteNowSlotUseCase(mongId = mongId)
            } catch (_: ErrorException) {
                uiState.isEvolution = false
            }
        }
    }

    fun graduationReady(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                graduateReadySlotUseCase(mongId = mongId)
            } catch (_: ErrorException) {
            }
        }
    }

    class UiState (
        isEvolution: Boolean = false,
    ) {
        var isEvolution by mutableStateOf(isEvolution)
    }
}