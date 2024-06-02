package com.paymong.wear.ui.viewModel.mainConfigure

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainConfigureViewModel @Inject constructor(
): ViewModel() {
    val uiState: UiState = UiState()

    class UiState () {}
}