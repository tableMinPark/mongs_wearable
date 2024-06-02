package com.paymong.wear.ui.viewModel.mainCondition

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainConditionViewModel @Inject constructor(
): ViewModel() {
    val uiState: UiState = UiState()

    class UiState () {}
}