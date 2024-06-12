package com.mongs.wear.ui.viewModel.paymentExchangePayPoint

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.PurchasesUpdatedListener
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentExchangeViewModel @Inject constructor(
): ViewModel() {
    val uiState: UiState = UiState()

    fun loadData(context: Context) {
        viewModelScope.launch (Dispatchers.IO) {
        }
    }

    class UiState (
//        navLoginView: Boolean = false,
//        loadingBar: Boolean = false,
//        logoutDialog: Boolean = false,
    ) {
//        var navLoginView by mutableStateOf(navLoginView)
//        var loadingBar by mutableStateOf(loadingBar)
//        var logoutDialog by mutableStateOf(logoutDialog)
    }
}