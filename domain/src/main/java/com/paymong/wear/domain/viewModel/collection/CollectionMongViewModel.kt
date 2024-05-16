package com.paymong.wear.domain.viewModel.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.DefaultValue
import com.paymong.wear.domain.repository.common.CodeRepository
import com.paymong.wear.domain.refac.vo.MongCodeVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionMongViewModel @Inject constructor(
    private val codeRepository: CodeRepository
) : ViewModel() {
    val mongCodeVo: LiveData<MongCodeVo> get() = _mongCodeVo
    private val _mongCodeVo = MutableLiveData(DefaultValue.MONG_CODE_VO)

    fun getMongCode(mongCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _mongCodeVo.postValue(codeRepository.getMongCode(code = mongCode))
        }
    }
}