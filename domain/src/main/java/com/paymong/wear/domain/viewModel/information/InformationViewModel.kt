package com.paymong.wear.domain.viewModel.information

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paymong.wear.domain.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
) : ViewModel() {
    var mongName by mutableStateOf(DefaultValue.mongName)
    var mongAge by mutableStateOf(DefaultValue.mongAge)
    var mongWeight by mutableStateOf(DefaultValue.mongWeight)

    init {
        mongAge = getAge(LocalDateTime.now())
    }

    private fun getAge(born: LocalDateTime): String {
        val day = ChronoUnit.DAYS.between(born, LocalDateTime.now())
        val hours = ChronoUnit.HOURS.between(born.plusDays(day), LocalDateTime.now())
        val minutes = ChronoUnit.MINUTES.between(born.plusDays(day).plusHours(hours), LocalDateTime.now())
        return "${day}일 ${hours}시간 ${minutes}분"
    }
}