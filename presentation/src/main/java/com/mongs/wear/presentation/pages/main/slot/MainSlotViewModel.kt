package com.mongs.wear.presentation.pages.main.slot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.errors.ManagerErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.slot.usecase.EvoluteMongUseCase
import com.mongs.wear.domain.slot.usecase.GraduateCheckMongUseCase
import com.mongs.wear.domain.slot.usecase.StrokeMongUseCase
import com.mongs.wear.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSlotViewModel @Inject constructor(
    private val strokeMongUseCase: StrokeMongUseCase,
    private val evoluteMongUseCase: EvoluteMongUseCase,
    private val graduateCheckMongUseCase: GraduateCheckMongUseCase,
): BaseViewModel() {

    fun stroke(mongId: Long) {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            strokeMongUseCase(mongId = mongId)
        }
    }

    fun evolution(mongId: Long) {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            evoluteMongUseCase(mongId = mongId)
        }
    }

    fun graduationReady(mongId: Long) {
        viewModelScopeWithHandler.launch (Dispatchers.IO) {
            graduateCheckMongUseCase(mongId = mongId)
        }
    }

    val uiState: UiState = UiState()

    class UiState (
        loadingBar: Boolean = false,
        isEvolution: Boolean = false,
    ) : BaseUiState() {

        var loadingBar by mutableStateOf(loadingBar)
        var isEvolution by mutableStateOf(isEvolution)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {

                ManagerErrorCode.MANAGER_EVOLUTION_MONG -> {
                    uiState.isEvolution = false
                }

                ManagerErrorCode.MANAGER_STROKE_MONG -> {}

                else -> {}
            }
        }
    }
}