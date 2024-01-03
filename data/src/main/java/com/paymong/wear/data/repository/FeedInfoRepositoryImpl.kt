package com.paymong.wear.data.repository

import androidx.lifecycle.LiveData
import com.paymong.wear.data.entity.Feed
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.repository.FeedInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class FeedInfoRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : FeedInfoRepository {
    override fun initSetFeedInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.feedDao().deleteAllFeed()

            // TODO : 밥, 스낵 인포 (API)

            val feedList = listOf(
                Feed(feedCode = "FD", code = "FD000", name = "FD000", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD010", name = "FD010", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD011", name = "FD011", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD012", name = "FD012", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD020", name = "FD020", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD021", name = "FD021", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD022", name = "FD022", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD030", name = "FD030", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN000", name = "SN000", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN001", name = "SN001", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN002", name = "SN002", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN010", name = "SN010", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN011", name = "SN011", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN012", name = "SN012", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN013", name = "SN013", buyRegDt = LocalDateTime.now().minusHours(1))
            )
            feedList.forEach {
                appDatabase.feedDao().registerFeed(it)
            }
        }
    }

    override fun getFoodList(): LiveData<List<FeedModel>> {
        return appDatabase.feedDao().findAllFeed("FD")
    }

    override fun getSnackList(): LiveData<List<FeedModel>> {
        return appDatabase.feedDao().findAllFeed("SN")
    }
}