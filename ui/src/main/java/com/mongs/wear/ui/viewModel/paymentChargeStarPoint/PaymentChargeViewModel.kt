package com.mongs.wear.ui.viewModel.paymentChargeStarPoint

import android.content.Context
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

    fun loadData(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    class UiState (
    ) {
    }
}