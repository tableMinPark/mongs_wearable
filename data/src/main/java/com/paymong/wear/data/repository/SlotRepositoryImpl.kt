package com.paymong.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.wear.data.retrofit.model.response.MongResModel
import com.paymong.wear.data.room.AppDatabase
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.domain.model.SlotModel
import com.paymong.wear.domain.repository.SlotRepository
import com.paymong.wear.domain.viewModel.DefaultValue
import java.time.LocalDateTime
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : SlotRepository {
    private var slotId = DefaultValue.slotId

    override fun initSlotInfo() {
        slotId = if (appDatabase.slotDao().count() > 0) {
            appDatabase.slotDao().findFirstBySlotId()
        } else {
            -1L
        }
    }
    override suspend fun getSlot(): LiveData<SlotModel> {
        return if (slotId == -1L) {
            MutableLiveData(SlotModel())
        } else {
            appDatabase.slotDao().findBySlotId(slotId = slotId)
        }
    }
    override suspend fun getAllSlot(): LiveData<List<SlotModel>> {
        return appDatabase.slotDao().findAll()
    }
    override suspend fun setSlot(slotId: Long) {
        this@SlotRepositoryImpl.slotId = slotId
    }
    override suspend fun generateSlot() {
        // TODO : 몽 생성 (API)
        val mongResModel = MongResModel(
            1L,
            LocalDateTime.now(),
            0,
            "CH000",
            "CH444",
            "CD000",
            "CD444",
            4,
            0.0f,
            0.0f,
            0.0f,
            0.0f
        )
        val newSlot = Slot(
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
        slotId = appDatabase.slotDao().register(newSlot)
    }
    override suspend fun removeSlot(slotId: Long) {
        appDatabase.slotDao().deleteBySlotId(slotId = slotId)
        if (this@SlotRepositoryImpl.slotId == slotId) {
            this@SlotRepositoryImpl.slotId = if (appDatabase.slotDao().count() > 0) {
                appDatabase.slotDao().findFirstBySlotId()
            } else {
                -1L
            }
        }
    }
    override suspend fun setSlotNextStateToState(stateCode: String) {
        val nowStateCode = appDatabase.slotDao().findStateBySlotId(slotId = slotId)
        appDatabase.slotDao().modifyNextStateBySlotId(nextStateCode = nowStateCode, slotId = slotId)
        appDatabase.slotDao().modifyStateBySlotId(stateCode = stateCode, slotId = slotId)
    }
    override suspend fun setSlotStateToNextState() {
        val nextStateCode = appDatabase.slotDao().findNextStateBySlotId(slotId = slotId)
        appDatabase.slotDao().modifyStateBySlotId(stateCode = nextStateCode, slotId = slotId)
        appDatabase.slotDao().modifyNextStateBySlotId(nextStateCode = "CD444", slotId = slotId)
    }
    override suspend fun setSlotMongCodeToNextMongCode() {
        val nextMongCode = appDatabase.slotDao().findNextMongCodeBySlotId(slotId = slotId)
        appDatabase.slotDao().modifyMongCodeBySlotId(mongCode = nextMongCode, slotId = slotId)
        appDatabase.slotDao().modifyNextMongCodeBySlotId(nextMongCode = "CH444", slotId = slotId)
    }


    override suspend fun getSlotMongState(): String {
        return appDatabase.slotDao().findStateBySlotId(slotId = slotId)
    }
    override suspend fun setSlotMongState(stateCode: String) {
        appDatabase.slotDao().modifyStateBySlotId(stateCode = stateCode, slotId = slotId)
    }
    override suspend fun setSlotMongPoopCount(poopCount: Int) {
        appDatabase.slotDao().modifyPoopCountBySlotId(poopCount = poopCount, slotId = slotId)
    }
    override suspend fun setSlotMongSleep() {
        val nextStateCode = appDatabase.slotDao().findStateBySlotId(slotId = slotId);
        appDatabase.slotDao().modifyNextStateBySlotId(nextStateCode = nextStateCode, slotId = slotId)
        appDatabase.slotDao().modifyStateBySlotId(stateCode = "CD002", slotId = slotId)
    }
    override suspend fun setSlotMongWakeUp() {
        val stateCode = appDatabase.slotDao().findNextStateBySlotId(slotId = slotId);
        appDatabase.slotDao().modifyNextStateBySlotId(nextStateCode = "CD444", slotId = slotId)
        appDatabase.slotDao().modifyStateBySlotId(stateCode = stateCode, slotId = slotId)
    }
}