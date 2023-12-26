package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.paymong.wear.domain.dto.DefaultCode
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConditionViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    var mongCode: LiveData<String> get() = _mongModel.map { it.mongCode }
    var health: LiveData<Float> get() = _mongModel.map { it.health }
    var satiety: LiveData<Float> get() = _mongModel.map { it.satiety }
    var strength: LiveData<Float> get() = _mongModel.map { it.strength }
    var sleep: LiveData<Float> get() = _mongModel.map { it.sleep }

    private val _mongModel = MediatorLiveData<MongModel>()
    private val liveDataObserver = Observer<LiveData<MongModel>> { innerLiveData ->
        innerLiveData.observeForever { mongModel ->
            Log.d("ConditionViewModel", mongModel.toString())
            _mongModel.value = mongModel
        }
    }

    init {
        mongCode = MutableLiveData(DefaultValue.mongCode)
        health = MutableLiveData(1.0f)
        satiety = MutableLiveData(1.0f)
        strength = MutableLiveData(1.0f)
        sleep = MutableLiveData(1.0f)
        _mongModel.addSource(mongRepository.getMong(), liveDataObserver)
    }
    override fun onCleared() {
        super.onCleared()
        mongRepository.getMong().removeObserver(liveDataObserver)
        Log.d("ConditionViewModel", "removeObserver")
    }
}