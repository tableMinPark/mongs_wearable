package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.usecase.EvoluteMongUseCase
import com.mongs.wear.domain.slot.usecase.GraduateCheckMongUseCase
import com.mongs.wear.domain.slot.usecase.StrokeMongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSlotViewModel @Inject constructor(
    private val strokeMongUseCase: StrokeMongUseCase,
    private val evoluteMongUseCase: EvoluteMongUseCase,
    private val graduateCheckMongUseCase: GraduateCheckMongUseCase,
): ViewModel() {

    val uiState: UiState = UiState()

    fun stroke(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                strokeMongUseCase(mongId = mongId)
            } catch (_: ErrorException) {
            }
        }
    }

    fun evolution(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                evoluteMongUseCase(mongId = mongId)
            } catch (_: ErrorException) {
                uiState.isEvolution = false
            }
        }
    }

    fun graduationReady(mongId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                graduateCheckMongUseCase(mongId = mongId)
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