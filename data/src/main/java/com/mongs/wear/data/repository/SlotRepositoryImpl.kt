package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.data.api.client.ManagementApi
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.Slot
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.parent.ApiException
import com.mongs.wear.domain.exception.parent.RoomException
import com.mongs.wear.domain.model.SlotModel
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): SlotRepository {
    override suspend fun setSlots(accountId: Long) {
        val res = managementApi.findMong()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                val selectedSlotMongId = if (roomDB.slotDao().countIsSelectedTrue() > 0) roomDB.slotDao().findByIsSelectedTrue().mongId else -1L
                roomDB.slotDao().deleteAll()

                body.forEach {
                    roomDB.slotDao().register(
                        Slot(
                            mongId = it.mongId,
                            name = it.name,
                            mongCode = it.mongCode,
                            weight = it.weight,
                            healthy = it.healthy,
                            satiety = it.satiety,
                            strength = it.strength,
                            sleep = it.sleep,
                            poopCount = it.poopCount,
                            isSleeping = it.isSleeping,
                            exp = it.exp,
                            stateCode = it.stateCode,
                            shiftCode = it.shiftCode,
                            payPoint = it.payPoint,
                            born = it.born,
                        )
                    )
                }

                if (selectedSlotMongId >= 0L && roomDB.slotDao().countByMongId(selectedSlotMongId) > 0) {
                    roomDB.slotDao().modifyIsSelectedByMongId(selectedSlotMongId, true)
                }
            }
        } else {
            throw ApiException(RepositoryErrorCode.SYNC_MONG_FAIL)
        }
    }
    override suspend fun getSlotsLive(): LiveData<List<SlotVo>> {
        return roomDB.slotDao().findAll().map { slotList ->
            slotList.map { slot ->
                SlotVo(
                    mongId = slot.mongId,
                    name = slot.name,
                    mongCode = slot.mongCode,
                    weight = slot.weight,
                    healthy = slot.healthy,
                    satiety = slot.satiety,
                    strength = slot.strength,
                    sleep = slot.sleep,
                    poopCount = slot.poopCount,
                    isSleeping = slot.isSleeping,
                    exp = slot.exp,
                    stateCode = slot.stateCode,
                    shiftCode = slot.shiftCode,
                    payPoint = slot.payPoint,
                    born = slot.born,
                    isHappy = slot.isHappy,
                    isEating = slot.isEating,
                    isSelected = slot.isSelected,
                    isGraduateCheck = slot.isGraduateCheck,
                )
            }
        }
    }
    override suspend fun setNowSlot(mongId: Long) {
        if (roomDB.slotDao().countIsSelectedTrue() > 0) {
            val selectedSlot = roomDB.slotDao().findByIsSelectedTrue()
            roomDB.slotDao().modifyIsSelectedByMongId(selectedSlot.mongId, false)
        }
        roomDB.slotDao().modifyIsSelectedByMongId(mongId, true)
    }
    override suspend fun getNowSlot(): SlotModel {
        if (roomDB.slotDao().countIsSelectedTrue() > 0) {
            roomDB.slotDao().findByIsSelectedTrue().let { slot ->
                return SlotModel(
                    mongId = slot.mongId,
                    name = slot.name,
                    mongCode = slot.mongCode,
                    weight = slot.weight,
                    healthy = slot.healthy,
                    satiety = slot.satiety,
                    strength = slot.strength,
                    sleep = slot.sleep,
                    poopCount = slot.poopCount,
                    isSleeping = slot.isSleeping,
                    exp = slot.exp,
                    stateCode = slot.stateCode,
                    shiftCode = slot.shiftCode,
                    payPoint = slot.payPoint,
                    born = slot.born,
                    isHappy = slot.isHappy,
                    isEating = slot.isEating,
                    isSelected = slot.isSelected,
                    isGraduateCheck = slot.isGraduateCheck,
                )
            }
        } else {
            throw RoomException(RepositoryErrorCode.NOT_FOUND_SELECTED_SLOT)
        }
    }
    override suspend fun getNowSlotLive(): LiveData<SlotModel> {
        if (roomDB.slotDao().countIsSelectedTrue() > 0) {
            roomDB.slotDao().findByIsSelectedTrueLive().let { slotLive ->
                return slotLive.map { slot ->
                    SlotModel(
                        mongId = slot.mongId,
                        name = slot.name,
                        mongCode = slot.mongCode,
                        weight = slot.weight,
                        healthy = slot.healthy,
                        satiety = slot.satiety,
                        strength = slot.strength,
                        sleep = slot.sleep,
                        poopCount = slot.poopCount,
                        isSleeping = slot.isSleeping,
                        exp = slot.exp,
                        stateCode = slot.stateCode,
                        shiftCode = slot.shiftCode,
                        payPoint = slot.payPoint,
                        born = slot.born,
                        isHappy = slot.isHappy,
                        isEating = slot.isEating,
                        isSelected = slot.isSelected,
                        isGraduateCheck = slot.isGraduateCheck,
                    )
                }
            }
        } else {
            throw RoomException(RepositoryErrorCode.NOT_FOUND_SELECTED_SLOT)
        }
    }
}