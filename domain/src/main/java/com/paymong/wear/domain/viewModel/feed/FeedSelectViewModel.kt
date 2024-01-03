package com.paymong.wear.domain.viewModel.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.viewModel.code.FeedSelectCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedSelectViewModel @Inject constructor(

) : ViewModel() {
    val processCode: LiveData<FeedSelectCode> get() = _processCode
    private val _processCode = MutableLiveData(FeedSelectCode.LOAD_FEED_LIST)

    var foodList: LiveData<List<FeedModel>> = MutableLiveData(ArrayList())
    var snackList: LiveData<List<FeedModel>> = MutableLiveData(ArrayList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val fl = listOf(
                FeedModel(foodCode = "0"),
                FeedModel(foodCode = "1"),
                FeedModel(foodCode = "2")
            )
            val sl = listOf(
                FeedModel(foodCode = "0"),
                FeedModel(foodCode = "1"),
                FeedModel(foodCode = "2")
            )

            foodList = MutableLiveData(fl)
            snackList = MutableLiveData(sl)

            delay(300)
            _processCode.postValue(FeedSelectCode.STAND_BY)
        }
    }

    fun feeding(feedCode: String) {
        Log.d("FeedSelectViewModel", "Call - feeding($feedCode)")
    }
}