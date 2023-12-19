package com.paymong.wear.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.data.api.model.response.MongResModel
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.data.room.entity.Mong
import com.paymong.wear.domain.dto.room.MongCondition
import com.paymong.wear.domain.dto.room.MongInfo
import com.paymong.wear.domain.dto.room.MongState
import com.paymong.wear.domain.repository.MongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class MongRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : MongRepository {
    var isDownloading = MutableLiveData(false)
    private var slotMaxCount = 0
    private var slotId: Int = 1
    private var isSlotEmpty = true

    private var mongCondition: LiveData<MongCondition> = MutableLiveData()
    private var mongInfo: LiveData<MongInfo> = MutableLiveData()
    private var mongState: LiveData<MongState> = MutableLiveData()

    override fun initMong(callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("test", "startInitMong")
            delay(5000)
            appDatabase.mongDao().deleteMongBySlotId(1)
            appDatabase.mongDao().deleteMongBySlotId(2)
            appDatabase.mongDao().deleteMongBySlotId(3)

//            delay(5000)
            // TODO : API 로 사용 가능한 슬롯 갯수 받아야 함
            this@MongRepositoryImpl.slotMaxCount = 3
            var slotCount = appDatabase.mongDao().countBySlotId()

            while (slotCount < slotMaxCount) {
                slotCount++
                appDatabase.mongDao().registerMong(
                    Mong(slotId = slotCount, mongCode = "CH444")
                )
            }

            // TODO : API 로 각 슬롯의 몽 정보를 받아야 함
            val mongResModelList = arrayListOf<MongResModel>(
                MongResModel(
                    1,
                    1L,
                    "1번 슬롯 몽",
                    LocalDateTime.now().minusHours(1),
                    11,
                    "CH100",
                    "CH444",
                    "CD000",
                    "CD444",
                    1,
                    0.3f,
                    0.3f,
                    0.3f,
                    0.3f
                ),
//                MongResModel(
//                    2,
//                    2L,
//                    "2번 슬롯 몽",
//                    LocalDateTime.now().minusHours(2),
//                    22,
//                    "CH200",
//                    "CH444",
//                    "CD000",
//                    "CD444",
//                    2,
//                    0.6f,
//                    0.6f,
//                    0.6f,
//                    0.6f
//                ),
//                MongResModel(
//                    3,
//                    3L,
//                    "3번 슬롯 몽",
//                    LocalDateTime.now().minusHours(3),
//                    33,
//                    "CH300",
//                    "CH444",
//                    "CD000",
//                    "CD444",
//                    3,
//                    0.9f,
//                    0.9f,
//                    0.9f,
//                    0.9f
//                )
            )
            for (mongResModel in mongResModelList) {
                appDatabase.mongDao().modifyMong(Mong.of(mongResModel))
            }

            setNowMong(slotId)
            callback()
            Log.d("test", "finushInitMongFun")
        }
    }

    override fun setNowMong(slotId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            this@MongRepositoryImpl.slotId = slotId
            mongCondition = appDatabase.mongDao().findMongConditionBySlotId(slotId)
            mongInfo = appDatabase.mongDao().findMongInfoBySlotId(slotId)
            mongState = appDatabase.mongDao().findMongStateBySlotId(slotId)
        }
    }

    override fun getMongCondition(): LiveData<MongCondition> {
        return this.mongCondition
    }

    override fun setMongCondition(mongCondition: MongCondition) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.mongDao().modifyMongConditionBySlotId(
                slotId = slotId,
                health = mongCondition.health,
                satiety = mongCondition.satiety,
                strength = mongCondition.satiety,
                sleep = mongCondition.sleep
            )
        }
    }

    override fun setMongInfo(mongInfo: MongInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.mongDao().modifyMongInfoBySlotId(
                slotId = slotId,
                mongName = mongInfo.mongName,
                born = mongInfo.born,
                weight = mongInfo.weight
            )
        }
    }

    override fun getMongInfo(): LiveData<MongInfo> {
        return this.mongInfo
    }

    override fun setMongState(mongState: MongState) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.mongDao().modifyMongStateBySlotId(
                slotId = slotId,
                stateCode = mongState.stateCode,
                nextStateCode = mongState.nextStateCode,
                poopCount = mongState.poopCount,
                mongCode = mongState.mongCode,
                nextMongCode = mongState.nextMongCode
            )
        }
    }

    override fun getMongState(): LiveData<MongState> {
        return this.mongState
    }
}