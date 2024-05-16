package com.paymong.wear.ui.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSlotViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val slotRepository: SlotRepository
) : ViewModel() {
    private var isStroking = false

    fun stroke() {
        if (!isStroking) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    isStroking = true
                    slotRepository.strokeNowSlot()
                    slotRepository.setNowSlotIsHappy(3000)
                } catch (e: ErrorException) {
                    feedbackRepository.addFeedbackLog(
                        groupCode = FeedbackCode.AUTH.groupCode,
                        location = "MainSlotViewModel#stroke",
                        message = e.errorCode.message(),
                    )
                } finally {
                    isStroking = false
                }
            }
        }
    }

    fun evolution() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotRepository.setNowSlotShiftCode(shiftCode = ShiftCode.EVOLUTION)
                slotRepository.evolutionNowSlot()
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.AUTH.groupCode,
                    location = "MainSlotViewModel#evolution",
                    message = e.errorCode.message(),
                )
            }
        }
    }

    fun graduationCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            slotRepository.graduationCheckNowSlot()
        }
    }
}