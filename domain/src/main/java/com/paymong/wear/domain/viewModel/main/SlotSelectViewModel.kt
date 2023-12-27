package com.paymong.wear.domain.viewModel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotSelectViewModel @Inject constructor(
    private val mongRepository: MongRepository
) : ViewModel() {
    val slotList: LiveData<List<MongModel>> = mongRepository.getAllMong()

    fun generateMong() {
        Log.d("SlotSelectViewModel", "Call - generateMong()")
        viewModelScope.launch(Dispatchers.IO) {
            mongRepository.generateMong()
        }
    }
}