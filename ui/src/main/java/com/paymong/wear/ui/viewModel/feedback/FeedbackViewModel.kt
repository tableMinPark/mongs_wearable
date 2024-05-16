package com.paymong.wear.ui.viewModel.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.error.FeedbackErrorCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.processCode.FeedbackProcessCode
import com.paymong.wear.domain.repository.common.CodeRepository
import com.paymong.wear.domain.refac.repository.common.vo.FeedbackCodeVo
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val codeRepository: CodeRepository,
) : ViewModel() {
    val processCode: LiveData<FeedbackProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(FeedbackProcessCode.STAND_BY)

    val feedbackItemMap: LiveData<Map<String, List<FeedbackCodeVo>>> get() = _feedbackItemMap
    private val _feedbackItemMap = MutableLiveData<Map<String, List<FeedbackCodeVo>>>()

    fun resetProcessCode() {
        _processCode.postValue(FeedbackProcessCode.STAND_BY)
    }

    fun loadFeedbackItemList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val feedbackCodeVoMap = HashMap<String, List<FeedbackCodeVo>>()

                codeRepository.getFeedbackGroupCode().forEach { groupCode ->
                    feedbackCodeVoMap[groupCode] =
                        codeRepository.getFeedbackCodeByGroupCode(groupCode = groupCode)
                }
                _feedbackItemMap.postValue(feedbackCodeVoMap)

                _processCode.postValue(FeedbackProcessCode.LOAD_FEEDBACK_ITEM_LIST_END)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.FEEDBACK.groupCode,
                    location = "FeedbackViewModel#loadFeedbackItemList",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(FeedbackProcessCode.LOAD_FEEDBACK_ITEM_LIST_FAIL)
            }
        }
    }

    fun feedback(code: String, groupCode: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                feedbackRepository.feedback(code = code, groupCode = groupCode, message = message)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.FEEDBACK.groupCode,
                    location = "FeedbackViewModel#feedback",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(FeedbackProcessCode.FEEDBACK_FAIL)
            }
        }
    }
}