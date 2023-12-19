package com.paymong.wear.domain.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paymong.wear.domain.dto.DefaultCode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConditionViewModel @Inject constructor(
//    private val mongRepository: MongRepository
) : ViewModel() {
    val mongCode: LiveData<String> get() = _mongCode
    private val _mongCode = MutableLiveData(DefaultCode.mongCode)
    val health: LiveData<Float> get() = _health
    private val _health = MutableLiveData(1.0f)
    val satiety: LiveData<Float> get() = _satiety
    private val _satiety = MutableLiveData(1.0f)
    val strength: LiveData<Float> get() = _strength
    private val _strength = MutableLiveData(1.0f)
    val sleep: LiveData<Float> get() = _sleep
    private val _sleep = MutableLiveData(1.0f)
}