package com.mongs.wear.presentation.pages.help.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HelpMenuViewModel @Inject constructor() : BaseViewModel() {

    val uiState = UiState()

    class UiState : BaseUiState() {
        var helpInfoDialog by mutableStateOf(false)
        var helpPointDialog by mutableStateOf(false)
        var helpMongDialog by mutableStateOf(false)
        var helpSlotDialog by mutableStateOf(false)
        var helpBattleDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {}
}