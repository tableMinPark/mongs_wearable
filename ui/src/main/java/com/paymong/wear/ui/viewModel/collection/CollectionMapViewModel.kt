package com.paymong.wear.ui.viewModel.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.repository.common.ConfigureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionMapViewModel @Inject constructor(
    private val configureRepository: ConfigureRepository
) : ViewModel() {

    fun mapSelect(mapCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            configureRepository.setBackgroundMapCode(mapCode = mapCode)
        }
    }
}