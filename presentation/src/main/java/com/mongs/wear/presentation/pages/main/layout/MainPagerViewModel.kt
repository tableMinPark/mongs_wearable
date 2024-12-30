package com.mongs.wear.presentation.pages.main.layout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mongs.wear.domain.common.usecase.GetBackgroundMapCodeUseCase
import com.mongs.wear.domain.slot.usecase.GetCurrentSlotUseCase
import com.mongs.wear.domain.slot.vo.SlotVo
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainPagerViewModel @Inject constructor(
    private val getCurrentSlotUseCase: GetCurrentSlotUseCase,
    private val getBackgroundMapCodeUseCase: GetBackgroundMapCodeUseCase,
): BaseViewModel() {

    private val _slotVo = MediatorLiveData<SlotVo?>(null)
    val slotVo: LiveData<SlotVo?> get() = _slotVo

    private val _backgroundMapCode = MediatorLiveData<String>()
    val backgroundMapCode: LiveData<String> get() = _backgroundMapCode

    init {
        viewModelScopeWithHandler.launch(Dispatchers.Main) {

            uiState.loadingBar = true

            _slotVo.addSource(withContext(Dispatchers.IO) { getCurrentSlotUseCase() }) {
                it?.let { slotVo ->
                    _slotVo.value = slotVo
                }
            }

            _backgroundMapCode.addSource(withContext(Dispatchers.IO) { getBackgroundMapCodeUseCase() }) { backgroundMapCode ->
                _backgroundMapCode.value = backgroundMapCode
            }

            uiState.loadingBar = false
        }
    }

    val uiState = UiState()

    class UiState : BaseUiState() {
        // 로딩바
        var loadingBar by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {}
}