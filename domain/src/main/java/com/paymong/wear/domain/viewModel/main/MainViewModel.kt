package com.paymong.wear.domain.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.DefaultValue
import com.paymong.wear.domain.processCode.MainProcessCode
import com.paymong.wear.domain.repository.common.ConfigureRepository
import com.paymong.wear.domain.repository.common.MemberRepository
import com.paymong.wear.domain.repository.slot.SlotRepository
import com.paymong.wear.domain.refac.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val configureRepository: ConfigureRepository,
    private val slotRepository: SlotRepository
) : ViewModel() {
    val processCode: LiveData<MainProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(MainProcessCode.STAND_BY)

    private var sound: LiveData<Float> = MutableLiveData(DefaultValue.SOUND)
    var backgroundMapCode: LiveData<String> = MutableLiveData(DefaultValue.BACKGROUND_MAP_CODE)
    var starPoint: LiveData<Int> = MutableLiveData(DefaultValue.STAR_POINT)
    var slotVo: LiveData<SlotVo> = MutableLiveData(DefaultValue.SLOT_VO)

    fun resetProcessCode() {
        _processCode.postValue(MainProcessCode.STAND_BY)
    }

    fun loadSlot() {
        viewModelScope.launch(Dispatchers.IO) {
            sound = configureRepository.getSound()
            backgroundMapCode = configureRepository.getBackgroundMapCode()
            starPoint = memberRepository.getStarPoint()
            slotVo = slotRepository.getNowSlot()
            _processCode.postValue(MainProcessCode.LOAD_SLOT_END)
        }
    }
}