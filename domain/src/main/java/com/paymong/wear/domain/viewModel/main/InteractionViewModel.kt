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
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class InteractionViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    var stateCode: LiveData<String> = MutableLiveData(DefaultValue.stateCode)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("InteractionViewModel", "InteractionViewModel - init!")
            val mongModel = mongRepository.getMong()
            stateCode = mongModel.map { it.stateCode }
        }
    }
}