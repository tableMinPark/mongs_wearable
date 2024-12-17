package com.mongs.wear.presentation.pages.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.core.enums.FeedbackCode
import com.mongs.wear.domain.repositroy.FeedbackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
) : ViewModel() {
    val uiState = UiState()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            uiState.loadingBar = false
        }
    }

    fun addFeedback(feedbackCode: FeedbackCode) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                feedbackRepository.createFeedback(title = feedbackCode.message, content = feedbackCode.secondaryMessage)
                uiState.okDialog = true
                uiState.addDialog = false
            } catch (_: ErrorException) {
                uiState.okDialog = true
                uiState.addDialog = false
            }
        }
    }

    class UiState (
        loadingBar: Boolean = true,
        addDialog: Boolean = false,
        okDialog: Boolean = false,
    ) {
        var loadingBar by mutableStateOf(loadingBar)
        var addDialog by mutableStateOf(addDialog)
        var okDialog by mutableStateOf(okDialog)
    }
}