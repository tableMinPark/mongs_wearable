package com.paymong.wear.ui.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainInteractionViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val slotRepository: SlotRepository
) : ViewModel() {
    fun sleep() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotRepository.sleepingNowSlot()
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "MainInteractionViewModel#sleep",
                    message = e.errorCode.message(),
                )
            }
        }
    }
    fun poop() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotRepository.poopCleanNowSlot()
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "MainInteractionViewModel#poop",
                    message = e.errorCode.message(),
                )
            }
        }
    }
}