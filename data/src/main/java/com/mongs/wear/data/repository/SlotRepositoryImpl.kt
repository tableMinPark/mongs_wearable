package com.mongs.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mongs.wear.data.api.client.ManagementApi
import com.mongs.wear.data.code.SlotShift
import com.mongs.wear.data.code.SlotState
import com.mongs.wear.data.room.client.RoomDB
import com.mongs.wear.data.room.entity.Slot
import com.mongs.wear.domain.error.RepositoryErrorCode
import com.mongs.wear.domain.exception.RepositoryException
import com.mongs.wear.domain.model.SlotModel
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val managementApi: ManagementApi,
    private val roomDB: RoomDB,
): SlotRepository {
    override suspend fun syncNowSlot() {
        try {
            roomDB.slotDao().selectByIsSelectedTrue()?.let { slot ->
                val mongId = slot.mongId
                val res = managementApi.findMong(mongId = mongId)

                if (res.isSuccessful) {
                    res.body()?.let { body ->
                        slot.mongId = body.mongId
                        slot.name = body.name
                        slot.mongCode = body.mongCode
                        slot.weight = body.weight
                        slot.healthy = body.healthy
                        slot.satiety = body.satiety
                        slot.strength = body.strength
                        slot.sleep = body.sleep
                        slot.poopCount = body.poopCount
                        slot.isSleeping = body.isSleeping
                        slot.exp = body.exp
                        slot.stateCode = SlotState.valueOf(body.stateCode).code
                        slot.shiftCode = SlotShift.valueOf(body.shiftCode).code
                        slot.payPoint = body.payPoint
                        slot.born = body.born
                        roomDB.slotDao().update(slot = slot)
                    }
                } else {
                    throw RepositoryException(RepositoryErrorCode.SYNC_SLOT_FAIL)
                }
            }
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SYNC_SLOT_FAIL,
                throwable = e,
            )
        }
    }
    override suspend fun setSlots(subScribeMong: suspend (Long) -> Unit) {
        val res = managementApi.findMong()

        if (res.isSuccessful) {
            res.body()?.let { body ->
                try {
                    val slots = roomDB.slotDao().selectAll()

                    val roomMongIds = slots.map { it.mongId }
                    val bodyMongIds = body.map { it.mongId }

                    slots.forEach { roomSlot ->
                        if (!bodyMongIds.contains(roomSlot.mongId)) {
                            roomDB.slotDao().deleteByMongId(mongId = roomSlot.mongId)
                        }
                    }

                    body.forEach { bodySlot ->
                        if (!roomMongIds.contains(bodySlot.mongId)) {
                            roomDB.slotDao().insert(
                                Slot(
                                    mongId = bodySlot.mongId,
                                    name = bodySlot.name,
                                    mongCode = bodySlot.mongCode,
                                    weight = bodySlot.weight,
                                    healthy = bodySlot.healthy,
                                    satiety = bodySlot.satiety,
                                    strength = bodySlot.strength,
                                    sleep = bodySlot.sleep,
                                    poopCount = bodySlot.poopCount,
                                    isSleeping = bodySlot.isSleeping,
                                    exp = bodySlot.exp,
                                    stateCode = bodySlot.stateCode,
                                    shiftCode = bodySlot.shiftCode,
                                    payPoint = bodySlot.payPoint,
                                    born = bodySlot.born,
                                )
                            )
                        } else {
                            roomDB.slotDao().selectByMongId(bodySlot.mongId)?.let { roomSlot ->
                                roomSlot.mongId = bodySlot.mongId
                                roomSlot.name = bodySlot.name
                                roomSlot.mongCode = bodySlot.mongCode
                                roomSlot.weight = bodySlot.weight
                                roomSlot.healthy = bodySlot.healthy
                                roomSlot.satiety = bodySlot.satiety
                                roomSlot.strength = bodySlot.strength
                                roomSlot.sleep = bodySlot.sleep
                                roomSlot.poopCount = bodySlot.poopCount
                                roomSlot.isSleeping = bodySlot.isSleeping
                                roomSlot.exp = bodySlot.exp
                                roomSlot.stateCode = SlotState.valueOf(bodySlot.stateCode).code
                                roomSlot.shiftCode = SlotShift.valueOf(bodySlot.shiftCode).code
                                roomSlot.payPoint = bodySlot.payPoint
                                roomSlot.born = bodySlot.born
                                roomDB.slotDao().update(slot = roomSlot)

                                if (roomSlot.isSelected) {
                                    subScribeMong(roomSlot.mongId)
                                }
                            }
                        }
                    }
                } catch (e: RuntimeException) {
                    throw RepositoryException(
                        errorCode = RepositoryErrorCode.SET_SLOTS_FAIL,
                        throwable = e,
                    )
                }
            }
        } else {
            throw RepositoryException(RepositoryErrorCode.SET_SLOTS_FAIL)
        }
    }
    override suspend fun getSlots(subScribeMong: suspend (Long) -> Unit): LiveData<List<SlotVo>> {
        try {
            this.setSlots(subScribeMong = subScribeMong)

            return roomDB.slotDao().selectAllLive().map { slotList ->
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
                        isPoopCleaning = slot.isPoopCleaning,
                        isGraduateCheck = slot.isGraduateCheck,
                    )
                }
            }
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_SLOTS_LIVE_FAIL,
                throwable = e,
            )
        }
    }

    override suspend fun getSlot(mongId: Long): SlotModel {
        try {
            roomDB.slotDao().selectByMongId(mongId = mongId)?.let { slot ->
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
                    isPoopCleaning = slot.isPoopCleaning,
                    isGraduateCheck = slot.isGraduateCheck,
                )
            }

            throw RepositoryException(RepositoryErrorCode.GET_NOT_SLOT_FAIL)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_NOT_SLOT_FAIL,
                throwable = e,
            )
        }
    }

    override suspend fun setNowSlot(subScribeMong: suspend (Long) -> Unit, mongId: Long) {
        try {
            roomDB.slotDao().selectByIsSelectedTrue()?.let { slot ->
                roomDB.slotDao().updateIsSelectedBySetNowSlot(slot.mongId, false)
            }
            roomDB.slotDao().updateIsSelectedBySetNowSlot(mongId, true)
            subScribeMong(mongId)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.SET_NOW_SLOT_FAIL,
                throwable = e,
            )
        }
    }

    override suspend fun getNowSlot(): SlotModel {
        try {
            roomDB.slotDao().selectByIsSelectedTrue()?.let { slot ->
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
                    isPoopCleaning = slot.isPoopCleaning,
                    isGraduateCheck = slot.isGraduateCheck,
                )
            }

            throw RepositoryException(RepositoryErrorCode.GET_NOT_SLOT_FAIL)
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_NOT_SLOT_FAIL,
                throwable = e,
            )
        }
    }

    override suspend fun getNowSlotLive(): LiveData<SlotModel?> {
        try {
            return roomDB.slotDao().selectByIsSelectedTrueLive().map {
                it?.let { slot ->
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
                        isPoopCleaning = slot.isPoopCleaning,
                        isGraduateCheck = slot.isGraduateCheck,
                    )
                } ?: run {
                    null
                }
            }
        } catch (e: RuntimeException) {
            throw RepositoryException(
                errorCode = RepositoryErrorCode.GET_NOT_SLOT_LIVE_FAIL,
                throwable = e,
            )
        }
    }
}