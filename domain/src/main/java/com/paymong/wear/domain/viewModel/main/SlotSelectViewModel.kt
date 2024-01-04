package com.paymong.wear.domain.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.repository.MongRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.SlotSelectCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotSelectViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository,
    private val mongRepository: MongRepository
) : ViewModel() {
    val processCode: LiveData<SlotSelectCode> get() = _processCode
    private val _processCode = MutableLiveData(SlotSelectCode.LOAD_MONG_LIST)

    var maxSlot: LiveData<Int> = MutableLiveData(DefaultValue.maxSlot)
    var slotList: LiveData<List<MongModel>> = MutableLiveData(ArrayList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val appInfoModel = appInfoRepository.getAppInfo()
            maxSlot = appInfoModel.map { it.maxSlot }

            slotList = mongRepository.getAllMong()
            delay(300)
            _processCode.postValue(SlotSelectCode.STAND_BY)
        }
    }

    fun setSlot(slotId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectCode.SET_SLOT)
            mongRepository.setSlotId(callback = { _processCode.postValue(SlotSelectCode.NAVIGATE) }, slotId = slotId)
        }
    }

    fun generateMong() {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(SlotSelectCode.GENERATE_MONG)
            mongRepository.generateMong(callback = { _processCode.postValue(SlotSelectCode.NAVIGATE) })
        }
    }
}