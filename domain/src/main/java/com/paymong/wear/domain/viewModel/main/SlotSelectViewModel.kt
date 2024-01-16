package com.paymong.wear.domain.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.model.SlotModel
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.SlotRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.SlotSelectCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotSelectViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val slotRepository: SlotRepository
) : ViewModel() {
    val processCode: LiveData<SlotSelectCode> get() = _processCode
    private val _processCode = MutableLiveData(SlotSelectCode.LOAD_MONG_LIST)

    var nowSlotId: LiveData<Long> = MutableLiveData(DefaultValue.slotId)
    var maxSlot: LiveData<Int> = MutableLiveData(DefaultValue.maxSlot)
    val mongModel: LiveData<MongModel> get() = _mongModel
    private val _mongModel = MutableLiveData(MongModel())
    var slotList: LiveData<List<SlotModel>> = MutableLiveData(ArrayList())

    init {
        maxSlot = appInfoRepository.getAppInfoMaxSlot()

        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.async {
                val mongModel = slotRepository.getSlot()
                nowSlotId = mongModel.map { it.slotId }
                slotList = slotRepository.getAllSlot()
            }.await()

            delay(300)
            _processCode.postValue(SlotSelectCode.STAND_BY)
        }
    }

    fun getMongInfo(mongCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mongModel = appInfoRepository.getMongInfo(code = mongCode)
            _mongModel.postValue(mongModel)
        }
    }

    fun setSlot(slotId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectCode.SET_SLOT)
            viewModelScope.async(Dispatchers.IO) { slotRepository.setSlot(slotId = slotId) }.await()
            _processCode.postValue(SlotSelectCode.NAVIGATE)
        }
    }

    fun generateMong() {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectCode.GENERATE_MONG)
            viewModelScope.async(Dispatchers.IO) { slotRepository.generateSlot() }.await()
            _processCode.postValue(SlotSelectCode.NAVIGATE)
        }
    }

    fun removeSlot(slotId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectCode.REMOVE_MONG)
            viewModelScope.async(Dispatchers.IO) { slotRepository.removeSlot(slotId = slotId) }.await()
            _processCode.postValue(SlotSelectCode.STAND_BY)
        }
    }
}