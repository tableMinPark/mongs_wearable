package com.mongs.wear.presentation.viewModel.paymentChargeStarPoint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PaymentChargeViewModel @Inject constructor(
): ViewModel() {
    val uiState: UiState = UiState()

    init {
        viewModelScope.launch(Dispatchers.Main) {
        }
    }

    class UiState (
    ) {
    }
}