package com.paymong.wear.domain.viewModel.figure

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paymong.wear.domain.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FigureViewModel @Inject constructor(
) : ViewModel() {
    var mongCode by mutableStateOf(DefaultValue.mongCode)
    var stateCode by mutableStateOf(DefaultValue.stateCode)
    var poopCount by mutableStateOf(DefaultValue.poopCount)

    fun stroke() {
        Log.d("stroke", "stroke active")
    }
}