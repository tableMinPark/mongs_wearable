package com.paymong.wear.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import com.paymong.wear.data.entity.Mong
import com.paymong.wear.data.entity.Feed
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.domain.model.AppInfoModel
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.repository.AppInfoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import javax.inject.Inject
import androidx.datastore.preferences.core.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.paymong.wear.data.dataStore.DataSource
import com.paymong.wear.domain.viewModel.DefaultValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlin.math.max


class AppInfoRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val dataSource: DataSource
) : AppInfoRepository {
    override suspend fun initSetAppInfo() {
        // TODO : 앱 인포 (API)
        val mapCode = "MP000"
        val maxSlot = 3
        val payPoint = 0

        dataSource.setMapCode(mapCode)
        dataSource.setMaxSlot(maxSlot)
        dataSource.setPayPoint(payPoint)
    }
    override suspend fun initSetConfigureInfo() {
        // TODO : Shared Storage 에서 앱 설정 값 설정
        dataSource.setSound()
    }
    override suspend fun initSetMongInfo() {
        appDatabase.mongDao().deleteAll()

        // TODO : 몽 관련 인포 (API)

        val mongLists = listOf(
            Mong(code = "CH000", name = "화산알", level = 0),
            Mong(code = "CH001", name = "석탄알", level = 0),
            Mong(code = "CH002", name = "황금알", level = 0),
            Mong(code = "CH003", name = "목성알", level = 0),
            Mong(code = "CH004", name = "지구알", level = 0),
            Mong(code = "CH005", name = "바람알", level = 0),

            Mong(code = "CH100", name = "별몽", level = 1),
            Mong(code = "CH101", name = "동글몽", level = 1),
            Mong(code = "CH102", name = "네몽", level = 1),

            Mong(code = "CH200", name = "안씻은 별별몽", level = 2),
            Mong(code = "CH210", name = "무난한 별별몽", level = 2),
            Mong(code = "CH220", name = "유쾌한 별별몽", level = 2),
            Mong(code = "CH230", name = "완벽한 별별몽", level = 2),
            Mong(code = "CH201", name = "안씻은 둥글몽", level = 2),
            Mong(code = "CH211", name = "무난한 둥글몽", level = 2),
            Mong(code = "CH221", name = "유쾌한 둥글몽", level = 2),
            Mong(code = "CH231", name = "완벽한 둥글몽", level = 2),
            Mong(code = "CH202", name = "안씻은 나네몽", level = 2),
            Mong(code = "CH212", name = "무난한 나네몽", level = 2),
            Mong(code = "CH222", name = "유쾌한 나네몽", level = 2),
            Mong(code = "CH232", name = "완벽한 나네몽", level = 2),
            Mong(code = "CH203", name = "까몽", level = 2),

            Mong(code = "CH300", name = "병든 별뿔몽", level = 3),
            Mong(code = "CH310", name = "평범한 별뿔몽", level = 3),
            Mong(code = "CH320", name = "매력적인 별뿔몽", level = 3),
            Mong(code = "CH330", name = "엘리트 별뿔몽", level = 3),
            Mong(code = "CH301", name = "병든 땡글몽", level = 3),
            Mong(code = "CH311", name = "평범한 땡글몽", level = 3),
            Mong(code = "CH321", name = "매력적인 땡글몽", level = 3),
            Mong(code = "CH331", name = "엘리트 땡글몽", level = 3),
            Mong(code = "CH302", name = "병든 마미무메몽", level = 3),
            Mong(code = "CH312", name = "평범한 마미무메몽", level = 3),
            Mong(code = "CH322", name = "매력적인 마미무메몽", level = 3),
            Mong(code = "CH332", name = "엘리트 마미무메몽", level = 3),
            Mong(code = "CH303", name = "쌔까몽", level = 3),

            Mong(code = "CH444", name = "", level = -1),
        )

        mongLists.forEach {
            appDatabase.mongDao().register(it)
        }
    }
    override suspend fun initSetFeedInfo() {
        appDatabase.feedDao().deleteAllFeed()

        // TODO : 밥, 스낵 인포 (API)

        val feedList = listOf(
            Feed(price = 1,feedCode = "FD", code = "FD000", name = "별사탕", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 2,feedCode = "FD", code = "FD010", name = "사과", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 3,feedCode = "FD", code = "FD011", name = "삼각김밥", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 4,feedCode = "FD", code = "FD012", name = "샌드위치", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 5,feedCode = "FD", code = "FD020", name = "피자", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 6,feedCode = "FD", code = "FD021", name = "치킨", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 7,feedCode = "FD", code = "FD022", name = "스테이크", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 8,feedCode = "FD", code = "FD030", name = "인삼", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 1,feedCode = "SN", code = "SN000", name = "초콜릿", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 2,feedCode = "SN", code = "SN001", name = "사탕", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 3,feedCode = "SN", code = "SN002", name = "콜라", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 4,feedCode = "SN", code = "SN010", name = "쿠키", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 5,feedCode = "SN", code = "SN011", name = "케이크", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 6,feedCode = "SN", code = "SN012", name = "감자튀김", buyRegDt = LocalDateTime.now().minusHours(1)),
            Feed(price = 7,feedCode = "SN", code = "SN013", name = "아이스크림", buyRegDt = LocalDateTime.now().minusHours(1))
        )
        feedList.forEach {
            appDatabase.feedDao().registerFeed(it)
        }
    }

    /** 몽 데이터 값 **/
    override suspend fun getMongInfo(code: String): MongModel {
        return appDatabase.mongDao().findByCode(code)
    }

    /** 음식 리스트 **/
    override suspend fun getFoodList(): LiveData<List<FeedModel>> {
        return appDatabase.feedDao().findAllFeed("FD")
    }

    /** 간식 리스트 **/
    override suspend fun getSnackList(): LiveData<List<FeedModel>> {
        return appDatabase.feedDao().findAllFeed("SN")
    }

    /** 사운드 설정 값 **/
    override fun getConfigureSound(): LiveData<Float> {
        return dataSource.getSound()
    }
    override suspend fun setConfigureSound(sound: Float) {
        dataSource.setSound(sound)
    }

    /** 맵 코드 값 **/
    override fun getAppInfoMapCode(): LiveData<String> {
        return dataSource.getMapCode()
    }

    /** 슬롯 최대 갯수 값 **/
    override fun getAppInfoMaxSlot(): LiveData<Int> {
        return dataSource.getMaxSlot()
    }

    /** 포인트 값 **/
    override fun getAppInfoPayPoint(): LiveData<Int> {
        return dataSource.getPayPoint()
    }

    /** 네트워크 연결 플래그 **/
    override fun getNetworkFlag(): LiveData<Boolean> {
        return dataSource.getNetworkFlag()
    }
}