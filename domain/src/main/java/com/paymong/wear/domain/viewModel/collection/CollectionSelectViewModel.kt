package com.paymong.wear.domain.viewModel.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.code.FeedbackCode
import com.paymong.wear.domain.exception.ErrorException
import com.paymong.wear.domain.repository.collection.vo.CollectionVo
import com.paymong.wear.domain.processCode.CollectionSelectProcessCode
import com.paymong.wear.domain.repository.collection.CollectionRepository
import com.paymong.wear.domain.repository.feedback.FeedbackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionSelectViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val collectionRepository: CollectionRepository
) : ViewModel() {
    val processCode: LiveData<CollectionSelectProcessCode> get() = _processCode
    private val _processCode = MutableLiveData(CollectionSelectProcessCode.STAND_BY)

    val collectionItemList: LiveData<List<CollectionVo>> get() = _collectionItemList
    private val _collectionItemList = MutableLiveData<List<CollectionVo>>()

    fun resetProcessCode() {
        _processCode.postValue(CollectionSelectProcessCode.STAND_BY)
    }

    fun loadCollectionItemList(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (code == "MONG") {
                    _collectionItemList.postValue(collectionRepository.getAllCollectionMong())
                } else {
                    _collectionItemList.postValue(collectionRepository.getAllCollectionMap())
                }

                _processCode.postValue(CollectionSelectProcessCode.LOAD_COLLECTION_ITEM_LIST_END)
            } catch (e: ErrorException) {
                feedbackRepository.addFeedbackLog(
                    groupCode = FeedbackCode.COLLECTION.groupCode,
                    location = "CollectionSelectViewModel#loadCollectionItemList",
                    message = e.errorCode.message(),
                )

                _processCode.postValue(CollectionSelectProcessCode.LOAD_COLLECTION_ITEM_LIST_FAIL)
            }
        }
    }
}