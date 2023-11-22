package com.paymong.wear.domain.viewModel.condition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paymong.wear.domain.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConditionViewModel @Inject constructor(
) : ViewModel() {
    var health by mutableStateOf(DefaultValue.health)
    var satiety by mutableStateOf(DefaultValue.satiety)
    var strength by mutableStateOf(DefaultValue.strength)
    var sleep by mutableStateOf(DefaultValue.sleep)
}