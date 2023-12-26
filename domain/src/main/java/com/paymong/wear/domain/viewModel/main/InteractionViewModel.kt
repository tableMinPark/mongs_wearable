package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import javax.inject.Inject

@HiltViewModel
class InteractionViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    var stateCode: LiveData<String> get() = _mongModel.map { it.stateCode }

    private val _mongModel = MediatorLiveData<MongModel>()
    private val liveDataObserver = Observer<LiveData<MongModel>> { innerLiveData ->
        innerLiveData.observeForever { mongModel ->
            Log.d("InteractionViewModel", mongModel.toString())
            _mongModel.value = mongModel
        }
    }

    init {
        stateCode = MutableLiveData(DefaultValue.stateCode)
        _mongModel.addSource(mongRepository.getMong(), liveDataObserver)
    }

    override fun onCleared() {
        super.onCleared()
        mongRepository.getMong().removeObserver(liveDataObserver)
        Log.d("InteractionViewModel", "removeObserver")
    }
}