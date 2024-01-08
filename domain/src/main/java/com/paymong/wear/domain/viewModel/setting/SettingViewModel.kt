package com.paymong.wear.domain.viewModel.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository
) : ViewModel()  {
    var sound: LiveData<Float> = MutableLiveData(DefaultValue.sound)

    init {
        sound = appInfoRepository.getConfigureSound()
    }

    fun setSound(value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            appInfoRepository.setConfigureSound(value)
        }
    }
}