package com.paymong.wear.ui.viewModel.mainSlot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.usecase.slot.EvoluteNowSlotUseCase
import com.paymong.wear.domain.usecase.slot.StrokeNowSlotUseCase
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

    class UiState () {}
}