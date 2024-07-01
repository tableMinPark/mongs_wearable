package com.mongs.wear.ui.viewModel.helpMenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HelpMenuViewModel @Inject constructor(

) : ViewModel() {
    val uiState = UiState()

    class UiState (
        helpPointDialog: Boolean = false,
        helpMongDialog: Boolean = false,
        helpSlotDialog: Boolean = false,
        helpBattleDialog: Boolean = false,
    ) {
        var helpPointDialog by mutableStateOf(helpPointDialog)
        var helpMongDialog by mutableStateOf(helpMongDialog)
        var helpSlotDialog by mutableStateOf(helpSlotDialog)
        var helpBattleDialog by mutableStateOf(helpBattleDialog)
    }
}