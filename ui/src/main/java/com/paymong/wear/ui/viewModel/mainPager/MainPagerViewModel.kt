package com.paymong.wear.ui.viewModel.mainPager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.usecase.configure.GetBackgroundMapCodeUseCase
import com.paymong.wear.domain.usecase.slot.GetNowSlotUseCase
import com.paymong.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPagerViewModel @Inject constructor(
    private val getNowSlotUseCase: GetNowSlotUseCase,
    private val getBackgroundMapCodeUseCase: GetBackgroundMapCodeUseCase,
): ViewModel() {
    val uiState: UiState = UiState()

    var slotVo: LiveData<SlotVo> = MutableLiveData()
    var backgroundMapCode: LiveData<String> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            slotVo = getNowSlotUseCase()
            backgroundMapCode = getBackgroundMapCodeUseCase()
            uiState.loadingBar = false
        }
    }

    class UiState (
        loadingBar: Boolean = true,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
    }
}