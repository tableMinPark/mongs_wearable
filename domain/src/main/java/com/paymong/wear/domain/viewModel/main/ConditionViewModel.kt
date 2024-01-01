package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.dto.DefaultCode
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class ConditionViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    var mongCode: LiveData<String> = MutableLiveData(DefaultValue.mongCode)
    var health: LiveData<Float> = MutableLiveData(DefaultValue.health)
    var satiety: LiveData<Float> = MutableLiveData(DefaultValue.satiety)
    var strength: LiveData<Float> = MutableLiveData(DefaultValue.strength)
    var sleep: LiveData<Float> = MutableLiveData(DefaultValue.sleep)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val mongModel = mongRepository.getMong()
            mongCode = mongModel.map { it.mongCode }
            health = mongModel.map { it.health }
            satiety = mongModel.map { it.satiety }
            strength = mongModel.map { it.strength }
            sleep = mongModel.map { it.sleep }
        }
    }
}