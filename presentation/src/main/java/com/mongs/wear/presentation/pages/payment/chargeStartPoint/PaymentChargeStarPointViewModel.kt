package com.mongs.wear.presentation.pages.payment.chargeStartPoint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PaymentChargeStarPointViewModel @Inject constructor(
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