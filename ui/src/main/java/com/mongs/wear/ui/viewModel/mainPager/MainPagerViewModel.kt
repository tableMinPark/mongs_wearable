package com.mongs.wear.ui.viewModel.mainPager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.configure.GetBackgroundMapCodeUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotUseCase
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainPagerViewModel @Inject constructor(
    private val getNowSlotUseCase: GetNowSlotUseCase,
    private val getBackgroundMapCodeUseCase: GetBackgroundMapCodeUseCase,
): ViewModel() {
    val uiState = UiState()

    val slotVo: LiveData<SlotVo?> get() = _slotVo
    private val _slotVo = MediatorLiveData<SlotVo?>(null)
    val backgroundMapCode: LiveData<String> get() = _backgroundMapCode
    private val _backgroundMapCode = MediatorLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                uiState.loadingBar = true

                _slotVo.addSource(
                    withContext(Dispatchers.IO) {
                        getNowSlotUseCase()
                    }
                ) { slotVo ->
                    _slotVo.value = slotVo
                }

                _backgroundMapCode.addSource(
                    withContext(Dispatchers.IO) {
                        getBackgroundMapCodeUseCase()
                    }
                ) { backgroundMapCode ->
                    _backgroundMapCode.value = backgroundMapCode
                }

                uiState.loadingBar = false

            } catch (_: UseCaseException) {
            }
        }
    }

    class UiState (
        loadingBar: Boolean = true,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
    }
}