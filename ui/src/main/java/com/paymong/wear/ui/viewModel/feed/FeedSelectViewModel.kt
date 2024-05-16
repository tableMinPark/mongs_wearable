package com.paymong.wear.ui.viewModel.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.processCode.FeedSelectProcessCode
import com.paymong.wear.domain.repository.common.CodeRepository
import com.paymong.wear.domain.vo.FoodVo
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
import com.paymong.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedSelectViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val slotRepository: SlotRepository,
    private val codeRepository: CodeRepository,
) : ViewModel() {
    val processCode: LiveData<FeedSelectProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(FeedSelectProcessCode.STAND_BY)

    val feedItemList: LiveData<List<FoodVo>> get() = _feedItemList
    private val _feedItemList = MutableLiveData<List<FoodVo>>()

    var slotVo: LiveData<SlotVo> = MutableLiveData(DefaultValue.SLOT_VO)

    fun resetProcessCode() {
        _processCode.postValue(FeedSelectProcessCode.STAND_BY)
    }

    fun loadFeedItemList(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                slotVo = slotRepository.getNowSlot()

                val foodVoList = ArrayList<FoodVo>()
                val feedHistoryMap = slotRepository.getFoodHistoryNowSlot().associateBy { it.code }

                codeRepository.getFoodCodeByGroupCode(groupCode = code).forEach { foodCodeVo ->
                    val feedHistory = feedHistoryMap[foodCodeVo.code]!!

                    foodVoList.add(
                        FoodVo(
                            code = foodCodeVo.code,
                            name = foodCodeVo.name,
                            price = foodCodeVo.price,
                            addWeight = foodCodeVo.addWeight,
                            addStrength = foodCodeVo.addStrength,
                            addSatiety = foodCodeVo.addSatiety,
                            addHealthy = foodCodeVo.addHealthy,
                            addSleep = foodCodeVo.addSleep,
                            isCanBuy = feedHistory.isCanBuy
                        )
                    )
                }
                _feedItemList.postValue(foodVoList)

                _processCode.postValue(FeedSelectProcessCode.LOAD_FEED_ITEM_LIST_END)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.FOOD.groupCode,
                    location = "FeedSelectViewModel#loadFeedItemList",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(FeedSelectProcessCode.LOAD_FEED_ITEM_LIST_FAIL)
            }
        }
    }
    fun feed(foodCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                slotRepository.feedNowSlot(foodCode = foodCode)
                slotRepository.setNowSlotIsEating(3000)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.FOOD.groupCode,
                    location = "FeedSelectViewModel#feed",
                    message = e.errorCode.message(),
                )
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.PAY_POINT.groupCode,
                    location = "FeedSelectViewModel#feed",
                    message = e.errorCode.message(),
                )
                _processCode.postValue(FeedSelectProcessCode.FEED_FAIL)
            }
        }
    }
}