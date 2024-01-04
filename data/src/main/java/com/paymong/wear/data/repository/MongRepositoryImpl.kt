package com.paymong.wear.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.data.api.model.response.MongResModel
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.data.entity.Mong
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import javax.inject.Inject

class MongRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : MongRepository {
    private var slotId = -1L

    override suspend fun getMong(): LiveData<MongModel> {
        val mongModel = if (slotId == -1L) {
            MutableLiveData(MongModel())
        } else {
            appDatabase.mongDao().findMongBySlotId(slotId = slotId)
        }

        return mongModel
    }
    override suspend fun getAllMong(): LiveData<List<MongModel>> {
        return appDatabase.mongDao().findAllMong()
    }
    override suspend fun initSetMong(callback: () -> Unit) {
        if (appDatabase.mongDao().countMong() > 0) {
            slotId = appDatabase.mongDao().findFirstMongSlotId()
        }
        callback()
    }
    override suspend fun generateMong(callback: () -> Unit) {
        // TODO : 몽 생성 (API)
        val mongResModel = MongResModel(
            1L,
            LocalDateTime.now(),
            11,
            "CH300",
            "CH444",
            "CD000",
            "CD444",
            4,
            0.1f,
            0.1f,
            0.1f,
            0.1f
        )
        val newMong = Mong(
            mongId = mongResModel.mongId,
            born = mongResModel.born,
            weight = mongResModel.weight,
            mongCode = mongResModel.mongCode,
            nextMongCode = mongResModel.nextMongCode,
            stateCode = mongResModel.stateCode,
            nextStateCode = mongResModel.nextStateCode,
            poopCount = mongResModel.poopCount,
            health = mongResModel.health,
            satiety = mongResModel.satiety,
            strength = mongResModel.strength,
            sleep = mongResModel.sleep
        )
        slotId = appDatabase.mongDao().registerMong(newMong)
        callback()
    }
    override suspend fun getSlotId(): Long {
        return this@MongRepositoryImpl.slotId
    }
    override suspend fun setSlotId(callback: () -> Unit, slotId: Long) {
        this@MongRepositoryImpl.slotId = slotId
        callback()
    }
    override suspend fun getMongState(): String {
        return appDatabase.mongDao().getMongState(slotId = slotId)
    }
    override suspend fun setMongState(stateCode: String) {
        appDatabase.mongDao().setMongState(stateCode = stateCode, slotId = slotId)
    }
    override suspend fun setPoopCount(poopCount: Int) {
        appDatabase.mongDao().setPoopCount(poopCount = poopCount, slotId = slotId)
    }
    override suspend fun setMongSleep() {
        val nextStateCode = appDatabase.mongDao().getMongState(slotId = slotId);
        appDatabase.mongDao().setMongNextState(nextStateCode = nextStateCode, slotId = slotId)
        appDatabase.mongDao().setMongState(stateCode = "CD002", slotId = slotId)
    }
    override suspend fun setMongWakeUp() {
        val stateCode = appDatabase.mongDao().getMongNextState(slotId = slotId);
        appDatabase.mongDao().setMongNextState(nextStateCode = "CD444", slotId = slotId)
        appDatabase.mongDao().setMongState(stateCode = stateCode, slotId = slotId)
    }
}