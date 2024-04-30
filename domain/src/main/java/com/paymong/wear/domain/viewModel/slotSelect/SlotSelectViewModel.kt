package com.paymong.wear.domain.viewModel.slotSelect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.slot.vo.SlotVo
import com.paymong.wear.domain.processCode.SlotSelectProcessCode
import com.paymong.wear.domain.DefaultValue
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.error.AuthErrorCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repository.common.MemberRepository
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotSelectViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val memberRepository: MemberRepository,
    private val slotRepository: SlotRepository
) : ViewModel() {
    val processCode: LiveData<SlotSelectProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(SlotSelectProcessCode.STAND_BY)

    var slotList: LiveData<List<SlotVo>> = MutableLiveData(ArrayList())
    val maxSlot: LiveData<Int> get() = _maxSlot
    private val _maxSlot = MutableLiveData(DefaultValue.MAX_SLOT)
    var starPoint: LiveData<Int> = MutableLiveData(DefaultValue.STAR_POINT)

    fun resetProcessCode() {
        _processCode.postValue(SlotSelectProcessCode.STAND_BY)
    }

    fun loadSlotList() {
        viewModelScope.launch(Dispatchers.IO) {
            _maxSlot.postValue(memberRepository.getMaxSlot())
            starPoint = memberRepository.getStarPoint()
            slotList = slotRepository.getAllSlot()

            _processCode.postValue(SlotSelectProcessCode.LOAD_SLOT_LIST_END)
        }
    }

    fun addSlot(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectProcessCode.ADD_SLOT)
            try {
                slotRepository.addSlot(name = name, sleepStart = "08:00", sleepEnd = "22:00")
                _processCode.postValue(SlotSelectProcessCode.CHANGE_SLOT_END)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "SlotSelectViewModel#addSlot",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(SlotSelectProcessCode.ADD_SLOT_FAIL)
            }
        }
    }

    fun selectSlot(mongId: Long) {
        _processCode.postValue(SlotSelectProcessCode.SET_SLOT)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotRepository.setNowSlot(mongId = mongId)
                _processCode.postValue(SlotSelectProcessCode.NAV_MAIN)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "SlotSelectViewModel#selectSlot",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(SlotSelectProcessCode.SET_SLOT_FAIL)
            }
        }
    }

    fun removeSlot(mongId: Long) {
        _processCode.postValue(SlotSelectProcessCode.REMOVE_SLOT)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotRepository.removeSlot(mongId = mongId)
                _processCode.postValue(SlotSelectProcessCode.CHANGE_SLOT_END)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "SlotSelectViewModel#removeSlot",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(SlotSelectProcessCode.REMOVE_SLOT_FAIL)
            }
        }
    }

    fun graduation(mongId: Long) {
        _processCode.postValue(SlotSelectProcessCode.REMOVE_SLOT)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotRepository.graduationSlot(mongId = mongId)
                _processCode.postValue(SlotSelectProcessCode.CHANGE_SLOT_END)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "SlotSelectViewModel#graduation",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(SlotSelectProcessCode.REMOVE_SLOT_FAIL)
            }
        }
    }

    fun buySlot() {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectProcessCode.BUY_SLOT)
            try {
                _processCode.postValue(SlotSelectProcessCode.STAND_BY)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.CHARACTER.groupCode,
                    location = "SlotSelectViewModel#buySlot",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(SlotSelectProcessCode.BUY_SLOT_FAIL)
            }
        }
    }
}