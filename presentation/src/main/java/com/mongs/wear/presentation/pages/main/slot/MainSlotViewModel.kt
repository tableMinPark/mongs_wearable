package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.slot.EvoluteNowSlotUseCase
import com.mongs.wear.domain.usecase.slot.GraduateReadySlotUseCase
import com.mongs.wear.domain.usecase.slot.StrokeNowSlotUseCase
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
            } catch (_: UseCaseException) {
            }
        }
    }

    fun evolution(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                evoluteNowSlotUseCase(mongId = mongId)
            } catch (_: UseCaseException) {
                uiState.isEvolution = false
            }
        }
    }

    fun graduationReady(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                graduateReadySlotUseCase(mongId = mongId)
            } catch (_: UseCaseException) {
            }
        }
    }

    class UiState (
        isEvolution: Boolean = false,
    ) {
        var isEvolution by mutableStateOf(isEvolution)
    }
}