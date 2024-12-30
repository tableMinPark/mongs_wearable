package com.mongs.wear.presentation.pages.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.core.enums.FeedbackCode
import com.mongs.wear.core.errors.UserErrorCode
import com.mongs.wear.core.exception.ErrorException
import com.mongs.wear.domain.feedback.usecase.CreateFeedbackUseCase
import com.mongs.wear.presentation.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val createFeedbackUseCase: CreateFeedbackUseCase
) : BaseViewModel() {

    fun createFeedback(feedbackCode: FeedbackCode, content: String) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            uiState.loadingBar = true
            uiState.inputDialog = false

            createFeedbackUseCase(title = feedbackCode.message, content = content)

            uiState.loadingBar = false
            uiState.confirmDialog = true
            uiState.addDialog = false
        }
    }

    val uiState = UiState()

    class UiState : BaseUiState() {
        var loadingBar by mutableStateOf(false)
        var inputDialog by mutableStateOf(false)
        var addDialog by mutableStateOf(false)
        var confirmDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        if (exception is ErrorException) {

            when (exception.code) {
                UserErrorCode.DATA_USER_CREATE_FEEDBACK -> {
                    uiState.loadingBar = false
                }
            }
        }
    }
}