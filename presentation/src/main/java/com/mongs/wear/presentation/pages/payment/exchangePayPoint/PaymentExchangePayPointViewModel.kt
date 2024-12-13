package com.mongs.wear.presentation.pages.payment.exchangePayPoint

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentExchangePayPointViewModel @Inject constructor(
): ViewModel() {
    val uiState: UiState = UiState()

    fun loadData(context: Context) {
        viewModelScope.launch (Dispatchers.IO) {
        }
    }

    class UiState (
    ) {
    }
}