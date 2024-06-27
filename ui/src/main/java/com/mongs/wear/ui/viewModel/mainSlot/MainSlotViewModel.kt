package com.mongs.wear.ui.viewModel.mainSlot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.slot.EvoluteNowSlotUseCase
import com.mongs.wear.domain.usecase.slot.GraduateReadySlotUseCase
import com.mongs.wear.domain.usecase.slot.StrokeNowSlotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSlotViewModel @Inject constructor(
    private val strokeNowSlotUseCase: StrokeNowSlotUseCase,
    private val evoluteNowSlotUseCase: EvoluteNowSlotUseCase,
    private val graduateReadySlotUseCase: GraduateReadySlotUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    fun stroke() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                strokeNowSlotUseCase()
            } catch (_: UseCaseException) {
            }
        }
    }

    fun evolution() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                evoluteNowSlotUseCase()
            } catch (_: UseCaseException) {
                uiState.isEvolution = false
            }
        }
    }

    fun graduationReady() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                graduateReadySlotUseCase()
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