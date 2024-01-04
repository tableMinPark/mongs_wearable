package com.paymong.wear.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.data.entity.Character
import com.paymong.wear.data.entity.Feed
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.domain.model.AppInfoModel
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.repository.AppInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : AppInfoRepository {
    private val appInfoModel: LiveData<AppInfoModel> get() = _appInfoModel
    private val _appInfoModel = MutableLiveData<AppInfoModel>()

    override fun initSetAppInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO : 앱 인포 (API)
            val mapCode = "MP000"
            val maxSlot = 3

            _appInfoModel.postValue(AppInfoModel(mapCode = mapCode, maxSlot = maxSlot))
        }
    }
    override fun initSetAppConfigureInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO : Shared Storage 에서 앱 설정 값 설정

        }
    }

    override fun initSetCharacterInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.characterDao().deleteAllCharacter()

            // TODO : 몽 관련 인포 (API)

            val characterList = listOf(
                Character(code = "CH000", name = "화산알", level = 0),
                Character(code = "CH001", name = "석탄알", level = 0),
                Character(code = "CH002", name = "황금알", level = 0),
                Character(code = "CH003", name = "목성알", level = 0),
                Character(code = "CH004", name = "지구알", level = 0),
                Character(code = "CH005", name = "바람알", level = 0),

                Character(code = "CH100", name = "별몽", level = 1),
                Character(code = "CH101", name = "동글몽", level = 1),
                Character(code = "CH102", name = "네몽", level = 1),

                Character(code = "CH200", name = "안씻은 별별몽", level = 2),
                Character(code = "CH210", name = "무난한 별별몽", level = 2),
                Character(code = "CH220", name = "유쾌한 별별몽", level = 2),
                Character(code = "CH230", name = "완벽한 별별몽", level = 2),
                Character(code = "CH201", name = "안씻은 둥글몽", level = 2),
                Character(code = "CH211", name = "무난한 둥글몽", level = 2),
                Character(code = "CH221", name = "유쾌한 둥글몽", level = 2),
                Character(code = "CH231", name = "완벽한 둥글몽", level = 2),
                Character(code = "CH202", name = "안씻은 나네몽", level = 2),
                Character(code = "CH212", name = "무난한 나네몽", level = 2),
                Character(code = "CH222", name = "유쾌한 나네몽", level = 2),
                Character(code = "CH232", name = "완벽한 나네몽", level = 2),
                Character(code = "CH203", name = "까몽", level = 2),

                Character(code = "CH300", name = "병든 별뿔몽", level = 3),
                Character(code = "CH310", name = "평범한 별뿔몽", level = 3),
                Character(code = "CH320", name = "매력적인 별뿔몽", level = 3),
                Character(code = "CH330", name = "엘리트 별뿔몽", level = 3),
                Character(code = "CH301", name = "병든 땡글몽", level = 3),
                Character(code = "CH311", name = "평범한 땡글몽", level = 3),
                Character(code = "CH321", name = "매력적인 땡글몽", level = 3),
                Character(code = "CH331", name = "엘리트 땡글몽", level = 3),
                Character(code = "CH302", name = "병든 마미무메몽", level = 3),
                Character(code = "CH312", name = "평범한 마미무메몽", level = 3),
                Character(code = "CH322", name = "매력적인 마미무메몽", level = 3),
                Character(code = "CH332", name = "엘리트 마미무메몽", level = 3),
                Character(code = "CH303", name = "쌔까몽", level = 3),

                Character(code = "CH444", name = "", level = -1),
            )

            characterList.forEach {
                appDatabase.characterDao().registerCharacter(it)
            }
        }
    }

    override fun initSetFeedInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.feedDao().deleteAllFeed()

            // TODO : 밥, 스낵 인포 (API)

            val feedList = listOf(
                Feed(feedCode = "FD", code = "FD000", name = "별사탕", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD010", name = "사과", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD011", name = "삼각김밥", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD012", name = "샌드위치", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD020", name = "피자", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD021", name = "치킨", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD022", name = "스테이크", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "FD", code = "FD030", name = "인삼", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN000", name = "초콜릿", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN001", name = "사탕", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN002", name = "콜라", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN010", name = "쿠키", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN011", name = "케이크", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN012", name = "감자튀김", buyRegDt = LocalDateTime.now().minusHours(1)),
                Feed(feedCode = "SN", code = "SN013", name = "아이스크림", buyRegDt = LocalDateTime.now().minusHours(1))
            )
            feedList.forEach {
                appDatabase.feedDao().registerFeed(it)
            }
        }
    }

    override fun getAppInfo() : LiveData<AppInfoModel> {
        return this@AppInfoRepositoryImpl.appInfoModel
    }
    override fun getFoodList(): LiveData<List<FeedModel>> {
        return appDatabase.feedDao().findAllFeed("FD")
    }
    override fun getSnackList(): LiveData<List<FeedModel>> {
        return appDatabase.feedDao().findAllFeed("SN")
    }
}