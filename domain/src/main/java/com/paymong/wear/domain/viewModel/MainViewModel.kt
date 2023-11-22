package com.paymong.wear.domain.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paymong.wear.domain.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    var mapCode by mutableStateOf(DefaultValue.mapCode)
    var mongCode by mutableStateOf(DefaultValue.mongCode)
    var stateCode by mutableStateOf(DefaultValue.stateCode)
}