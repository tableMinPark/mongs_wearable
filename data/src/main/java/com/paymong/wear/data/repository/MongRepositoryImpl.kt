package com.paymong.wear.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.data.api.model.response.MongResModel
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.data.entity.Mong
import com.paymong.wear.domain.model.MongModel
import com.paymong.wear.domain.repository.MongRepository
import java.time.LocalDateTime
import javax.inject.Inject

class MongRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : MongRepository {
    private var slotId = -1L
    private val mongModel: LiveData<LiveData<MongModel>> get() = _mongModel
    private val _mongModel: MutableLiveData<LiveData<MongModel>> = MutableLiveData<LiveData<MongModel>>()

    override suspend fun initSetMong() {
        if (appDatabase.mongDao().countMong() > 0) {
            val firstMongSlotId = appDatabase.mongDao().findFirstMongSlotId()
            setSlot(slotId = firstMongSlotId)
        }
    }
    override suspend fun generateMong() {
        // TODO : 몽 생성 (API)
        val mongResModel = MongResModel(
            1L,
            LocalDateTime.now(),
            11,
            "CH100",
            "CH444",
            "CD000",
            "CD444",
            1,
            0.5f,
            0.5f,
            0.5f,
            0.5f
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
        val registerMongSlotId = appDatabase.mongDao().registerMong(newMong)
        Log.d("MongRepository", "Call - generateMong() : $registerMongSlotId")
        setSlot(registerMongSlotId)
    }
    override suspend fun setSlot(slotId: Long) {
        Log.d("MongRepository", "Call - setSlot() : $slotId")
        this@MongRepositoryImpl.slotId = slotId
        setMong(slotId)
    }

    override fun getMong(): LiveData<LiveData<MongModel>> {
        return this@MongRepositoryImpl.mongModel
    }

    override fun getAllMong(): LiveData<List<MongModel>> {
        return appDatabase.mongDao().findAllMong()
    }

    override suspend fun setMongState(stateCode: String) {
        appDatabase.mongDao().setMongState(stateCode = stateCode, slotId = slotId)
    }

    override suspend fun findMongState(): String {
        return appDatabase.mongDao().getMongState(slotId = slotId)
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

    private fun setMong(slotId: Long) {
        val mongModel = appDatabase.mongDao().findBySlotId(slotId)
        this@MongRepositoryImpl._mongModel.postValue(mongModel)
        Log.d("MongRepository", "Call - setMong()")
    }
}