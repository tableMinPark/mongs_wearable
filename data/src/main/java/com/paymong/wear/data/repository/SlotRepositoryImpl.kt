package com.paymong.wear.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.paymong.wear.data.api.client.ManagementApi
import com.paymong.wear.data.api.client.MqttApi
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.domain.error.RepositoryErrorCode
import com.paymong.wear.domain.exception.ApiException
import com.paymong.wear.domain.exception.RoomException
import com.paymong.wear.domain.model.SlotModel
import com.paymong.wear.domain.repositroy.SlotRepository
import com.paymong.wear.domain.vo.SlotVo
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val managementApi: ManagementApi,
): SlotRepository {
    override suspend fun setSlots(accountId: Long) {
        val res = managementApi.findMong()

        if (res.isSuccessful) {
            res.body()?.let { body ->
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