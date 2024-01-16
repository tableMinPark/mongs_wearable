package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.repository.AppInfoRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.FeedSelectCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InteractionViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {
    var payPoint: LiveData<Int> = MutableLiveData(DefaultValue.payPoint)

    init {
        payPoint = appInfoRepository.getAppInfoPayPoint()
    }
}