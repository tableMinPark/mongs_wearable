package com.paymong.wear.ui.viewModel.mainInteraction

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainInteractionViewModel @Inject constructor(
): ViewModel() {
    val uiState: UiState = UiState()

    class UiState () {}
}