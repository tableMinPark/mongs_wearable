package com.paymong.wear.domain.viewModel_.initLanding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.processCode.InitLandingProcessCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitLandingViewModel @Inject constructor(

) : ViewModel() {
    val processCode: LiveData<InitLandingProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(InitLandingProcessCode.GOOGLE_SIGN_IN_CHECK)

    fun mongLifeLogin(userName: String?, email: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _processCode.postValue(InitLandingProcessCode.LOGIN_SUCCESS)
            Log.d("test", "userName: $userName, email: $email")
            _processCode.postValue(InitLandingProcessCode.NAV_MAIN)
        }
    }
    fun googleSignInCheck() {
        _processCode.postValue(InitLandingProcessCode.GOOGLE_SIGN_IN_CHECK)
    }
    fun googleSignIn() {
        _processCode.postValue(InitLandingProcessCode.LOGIN)
    }
    fun googleSignInFail() {
        _processCode.postValue(InitLandingProcessCode.GOOGLE_SIGN_IN_FAIL)
    }
}