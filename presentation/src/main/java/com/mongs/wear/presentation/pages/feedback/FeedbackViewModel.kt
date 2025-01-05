package com.mongs.wear.presentation.pages.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mongs.wear.core.enums.FeedbackCode
import com.mongs.wear.domain.feedback.exception.CreateFeedbackException
import com.mongs.wear.domain.feedback.usecase.CreateFeedbackUseCase
import com.mongs.wear.presentation.global.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val createFeedbackUseCase: CreateFeedbackUseCase
) : BaseViewModel() {

    init {
        viewModelScopeWithHandler.launch(Dispatchers.Main) {
            uiState.loadingBar = false
        }
    }

    fun createFeedback(feedbackCode: FeedbackCode, content: String) {
        viewModelScopeWithHandler.launch(Dispatchers.IO) {

            uiState.loadingBar = true
            uiState.inputDialog = false

            createFeedbackUseCase(
                CreateFeedbackUseCase.Param(
                    title = feedbackCode.message, content = content
                )
            )

            uiState.loadingBar = false
            uiState.confirmDialog = true
            uiState.addDialog = false
        }
    }

    val uiState = UiState()

    class UiState : BaseUiState() {
        var inputDialog by mutableStateOf(false)
        var addDialog by mutableStateOf(false)
        var confirmDialog by mutableStateOf(false)
    }

    override fun exceptionHandler(exception: Throwable) {

        when(exception) {
            is CreateFeedbackException -> {
                uiState.loadingBar = false
            }

            else -> {
                uiState.loadingBar = false
            }
        }
    }
}