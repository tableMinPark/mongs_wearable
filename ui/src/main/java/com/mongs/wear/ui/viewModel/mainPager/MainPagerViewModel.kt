package com.mongs.wear.ui.viewModel.mainPager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.configure.GetBackgroundMapCodeUseCase
import com.mongs.wear.domain.usecase.slot.GetNowSlotUseCase
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainPagerViewModel @Inject constructor(
    private val getNowSlotUseCase: GetNowSlotUseCase,
    private val getBackgroundMapCodeUseCase: GetBackgroundMapCodeUseCase,
): ViewModel() {
    val uiState = UiState()

    var slotVo: LiveData<SlotVo> = MutableLiveData()
    var backgroundMapCode: LiveData<String> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotVo = getNowSlotUseCase()
                backgroundMapCode = getBackgroundMapCodeUseCase()
                delay(800)
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