package com.paymong.wear.domain.viewModel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.viewModel.code.ConfigureCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigureViewModel @Inject constructor(
) : ViewModel() {
    val processCode: LiveData<ConfigureCode> get() = _processCode
    private val _processCode = MutableLiveData(ConfigureCode.STAND_BY)

    fun googleSignOutProcess() {
        _processCode.postValue(ConfigureCode.SIGN_OUT_PROCESS)
    }
    fun googleSignOutSuccess() {
        viewModelScope.launch {
            delay(2000)
            _processCode.postValue(ConfigureCode.SIGN_OUT_SUCCESS)
        }
    }
    fun googleSignOutFail() {
        _processCode.postValue(ConfigureCode.SIGN_OUT_FAIL)
    }
    fun googleSignOutEnd() {
        _processCode.postValue(ConfigureCode.NAVIGATE)
    }
}
