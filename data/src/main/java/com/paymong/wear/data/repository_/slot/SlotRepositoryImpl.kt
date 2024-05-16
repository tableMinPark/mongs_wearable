package com.paymong.wear.data.repository_.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.paymong.wear.data.api.client.ManagementApi
import com.paymong.wear.data.dto.management.request.FeedMongReqDto
import com.paymong.wear.data.dto.management.request.RegisterMongReqDto
import com.paymong.wear.data.dataStore.DeviceDataStore
import com.paymong.wear.data.code.Shift
import com.paymong.wear.data.code.State
import com.paymong.wear.data.room.client.RoomDB
import com.paymong.wear.data.room.entity.Slot
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.code.StateCode
import com.paymong.wear.domain.error.CommonErrorCode
import com.paymong.wear.domain.error.ManagementErrorCode
import com.paymong.wear.domain.exception.CommonException
import com.paymong.wear.domain.exception.ManagementException
import com.paymong.wear.domain.refac.repository.common.vo.FeedHistoryVo
import com.paymong.wear.domain.repository.slot.SlotRepository
import com.paymong.wear.domain.vo.SlotVo
import kotlinx.coroutines.delay
import javax.inject.Inject

class SlotRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB,
    private val deviceDataStore: DeviceDataStore,
    private val managementApi: ManagementApi
) : SlotRepository {
    
    override suspend fun initializeSlot() {        
        val response = managementApi.findAllMong()

        if (response.isSuccessful) {
            response.body()?.let { findMongResDtoList ->
                // ########## 슬롯 데이터 동기화 ##########
                val activeMongIdList = ArrayList<Long>()
                
                findMongResDtoList.forEach { findMongResDto ->
                    val newSlot = Slot(
                        mongId = findMongResDto.mongId,
                        name = findMongResDto.name,
                        mongCode = findMongResDto.mongCode,
                        weight = findMongResDto.weight,
                        healthy = findMongResDto.healthy,
                        satiety = findMongResDto.satiety,
                        strength = findMongResDto.strength,
                        sleep = findMongResDto.sleep,
                        poopCount = findMongResDto.poopCount,
                        isSleeping = findMongResDto.isSleeping,
                        exp = findMongResDto.exp,
                        stateCode = findMongResDto.stateCode,
                        shiftCode = findMongResDto.shiftCode,
                        payPoint = findMongResDto.payPoint,
                        born = findMongResDto.born,
                    )
                    val slot = roomDB.slotDao().findByMongId(newSlot.mongId)
                    if (slot == null) {
                        // room 에 없을 시 추가
                        roomDB.slotDao().register(slot = newSlot)
                    } else {
                        // room 에 있으면 값 수정
                        newSlot.isSelected = slot.isSelected
                        newSlot.isGraduateCheck = slot.isGraduateCheck
                        roomDB.slotDao().modifySlot(slot = newSlot)
                    }

                    // 활성화 된 mongId 저장
                    activeMongIdList.add(newSlot.mongId)
                }

                // 비활성화 된 잔여 mong 삭제 (삭제 or 죽음)
                roomDB.slotDao().deleteByMongIdIn(activeMongIdList)
            }
        } else {
            throw ManagementException(ManagementErrorCode.INIT_SET_NOW_SLOT_FAIL)
        }
    }

    override suspend fun setNowSlot() {
        if (roomDB.slotDao().countIsSelectedTrue() == 0) {
            // 선택된 슬롯이 없는 경우 첫 번째 슬롯으로 설정
            roomDB.slotDao().findTopMongIdOrderByMongId()?.let { mongId ->
                roomDB.slotDao().modifyIsSelectedByMongId(mongId = mongId, isSelected = true)
            }
        }
    }

    override suspend fun setNowSlot(mongId: Long) {
        // 기존 선택 슬롯 해제
        if (roomDB.slotDao().countIsSelectedTrue() > 0) {
            roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowMongId ->
                roomDB.slotDao().modifyIsSelectedByMongId(mongId = nowMongId, isSelected = false)
            }
        }
        // 새로운 선택 슬롯 설정
        roomDB.slotDao().modifyIsSelectedByMongId(mongId = mongId, isSelected = true)
    }

    override suspend fun getNowSlot(): LiveData<SlotVo> {
        return if (roomDB.slotDao().countIsSelectedTrue() == 0) {
            MutableLiveData(
                SlotVo(
                    mongId = DefaultValue.MONG_ID,
                    name = DefaultValue.NAME,
                    mongCode = DefaultValue.MONG_CODE,
                    weight = DefaultValue.WEIGHT,
                    healthy = DefaultValue.HEALTHY,
                    satiety = DefaultValue.SATIETY,
                    strength = DefaultValue.STRENGTH,
                    sleep = DefaultValue.SLEEP,
                    poopCount = DefaultValue.POOP_COUNT,
                    isSleeping = DefaultValue.IS_SLEEPING,
                    exp = DefaultValue.EXP,
                    stateCode = DefaultValue.STATE_CODE,
                    shiftCode = DefaultValue.SHIFT_CODE,
                    payPoint = DefaultValue.PAY_POINT,
                    born = DefaultValue.BORN,
                    isHappy = DefaultValue.IS_HAPPY,
                    isEating = DefaultValue.IS_EATING,
                    isSelected = DefaultValue.IS_SELECTED,
                    isGraduateCheck = DefaultValue.IS_GRADUATION_CHECK,
                )
            )
        } else {
            roomDB.slotDao().findByIsSelectedTrue().let { slot ->
                return slot.map {
                    SlotVo(
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
                        isHappy = it.isHappy,
                        isEating = it.isEating,
                        isSelected = it.isSelected,
                        isGraduateCheck = it.isGraduateCheck,
                    )
                }
            }
        }
    }

    override suspend fun getAllSlot(): LiveData<List<SlotVo>> {
        roomDB.slotDao().deleteByIsDeletedTrue()
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
                )
            }
        }
    }

    override suspend fun addSlot(name: String, sleepStart: String, sleepEnd: String): SlotVo {
        val response = managementApi.registerMong(
            RegisterMongReqDto(
                name = name,
                sleepStart = sleepStart,
                sleepEnd = sleepEnd,
            )
        )

        if (response.isSuccessful) {
            response.body()?.let { registerMongResDto ->
                val slot = Slot(
                    mongId = registerMongResDto.mongId,
                    name = registerMongResDto.name,
                    mongCode = registerMongResDto.mongCode,
                    weight = registerMongResDto.weight,
                    healthy = registerMongResDto.healthy,
                    satiety = registerMongResDto.satiety,
                    strength = registerMongResDto.strength,
                    sleep = registerMongResDto.sleep,
                    poopCount = registerMongResDto.poopCount,
                    isSleeping = registerMongResDto.isSleeping,
                    exp = registerMongResDto.exp,
                    stateCode = registerMongResDto.stateCode,
                    shiftCode = registerMongResDto.shiftCode,
                    payPoint = registerMongResDto.payPoint,
                    born = registerMongResDto.born,
                )

                if (roomDB.slotDao().findByMongId(registerMongResDto.mongId) == null) {
                    roomDB.slotDao().register(slot = slot)
                }

                return SlotVo(
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
                )
            }
        } 
        
        throw ManagementException(ManagementErrorCode.STROKE_FAIL)
    }

    override suspend fun removeSlot(mongId: Long) {
        val response = managementApi.deleteMong(mongId = mongId)

        if (response.isSuccessful) {
            response.body()?.let { deleteMongResDto ->
                val deleteMongId = deleteMongResDto.mongId
//                val shiftCode = deleteMongResDto.shiftCode

                roomDB.slotDao().deleteByMongId(deleteMongId)
            }
        } else {
            throw ManagementException(ManagementErrorCode.STROKE_FAIL)
        }
    }

    override suspend fun setNowSlotStateCode(stateCode: StateCode) {
        roomDB.slotDao().modifyStateCodeIsSelectedTrue(stateCode = stateCode)
    }

    override suspend fun getNowSlotStateCode(): String {
        return roomDB.slotDao().findStateCodeIsSelectedTrue()
    }

    override suspend fun setNowSlotShiftCode(shiftCode: ShiftCode) {
        roomDB.slotDao().modifyShiftCodeIsSelectedTrue(shiftCode = shiftCode)
    }

    override suspend fun getNowSlotShiftCode(): String {
        return roomDB.slotDao().findShiftCodeIsSelectedTrue()
    }

    override suspend fun setNowSlotIsHappy(delay: Long) {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { mongId ->
            roomDB.slotDao().modifyIsHappyByMongId(isHappy = true, mongId = mongId)
            delay(delay)
            roomDB.slotDao().modifyIsHappyByMongId(isHappy = false, mongId = mongId)
        }
    }

    override suspend fun setNowSlotIsEating(delay: Long) {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { mongId ->
            roomDB.slotDao().modifyIsEatingByMongId(isEating = true, mongId = mongId)
            delay(delay)
            roomDB.slotDao().modifyIsEatingByMongId(isEating = false, mongId = mongId)
        }
    }

    override suspend fun strokeNowSlot() {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            val response = managementApi.strokeMong(mongId = nowSlotMongId)

            if (response.isSuccessful) {
                response.body()?.let { strokeMongResDto ->
                    val mongId = strokeMongResDto.mongId
                    val exp = strokeMongResDto.exp

                    roomDB.slotDao().modifyAfterStroke(exp = exp, mongId = mongId)
                }
            } else {
                throw ManagementException(ManagementErrorCode.STROKE_FAIL)
            }
        }
    }

    override suspend fun feedNowSlot(foodCode: String) {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            val response = managementApi.feedMong(mongId = nowSlotMongId, feedMongReqDto = FeedMongReqDto(foodCode = foodCode))

            if (response.isSuccessful) {
                response.body()?.let { feedMongResDto ->
                    val mongId = feedMongResDto.mongId
                    val weight = feedMongResDto.weight
                    val strength = feedMongResDto.strength
                    val satiety = feedMongResDto.satiety
                    val healthy = feedMongResDto.healthy
                    val sleep = feedMongResDto.sleep
                    val exp = feedMongResDto.exp
                    val payPoint = feedMongResDto.payPoint

                    roomDB.slotDao().modifyAfterFeed(
                        mongId = mongId,
                        weight = weight,
                        strength = strength,
                        satiety = satiety,
                        healthy = healthy,
                        sleep = sleep,
                        exp = exp,
                        payPoint = payPoint
                    )
                }
            } else {
                throw ManagementException(ManagementErrorCode.FEED_FAIL)
            }
        }
    }

    override suspend fun sleepingNowSlot() {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            val response = managementApi.sleepingMong(mongId = nowSlotMongId)

            if (response.isSuccessful) {
                response.body()?.let { sleepingMongResDto ->
                    val mongId = sleepingMongResDto.mongId
                    val isSleeping = sleepingMongResDto.isSleeping

                    roomDB.slotDao().modifyAfterSleeping(mongId = mongId, isSleeping = isSleeping)
                }
            } else {
                throw ManagementException(ManagementErrorCode.SLEEPING_FAIL)
            }
        }
    }

    override suspend fun poopCleanNowSlot() {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            val response = managementApi.poopClean(mongId = nowSlotMongId)

            if (response.isSuccessful) {
                response.body()?.let { poopCleanResDto ->
                    val mongId = poopCleanResDto.mongId
                    val poopCount = poopCleanResDto.poopCount
                    val exp = poopCleanResDto.exp

                    roomDB.slotDao().modifyAfterPoopClean(mongId = mongId, poopCount = poopCount, exp = exp)
                }
            } else {
                throw ManagementException(ManagementErrorCode.POOP_CLEAN_FAIL)
            }
        }
    }

    override suspend fun evolutionNowSlot() {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            val response = managementApi.evolutionMong(mongId = nowSlotMongId)

            if (response.isSuccessful) {
                response.body()?.let { evolutionMongResDto ->
                    val mongId = evolutionMongResDto.mongId
                    val mongCode = evolutionMongResDto.mongCode
                    val weight = evolutionMongResDto.weight
                    val strength = evolutionMongResDto.strength
                    val satiety = evolutionMongResDto.satiety
                    val healthy = evolutionMongResDto.healthy
                    val sleep = evolutionMongResDto.sleep
                    val shiftCode = evolutionMongResDto.shiftCode
                    val stateCode = evolutionMongResDto.stateCode
                    val exp = evolutionMongResDto.exp

                    roomDB.slotDao().modifyAfterEvolution(
                        mongId = mongId,
                        mongCode = mongCode,
                        weight = weight,
                        strength = strength,
                        satiety = satiety,
                        healthy = healthy,
                        sleep = sleep,
                        shiftCode = Shift.valueOf(shiftCode).code,
                        stateCode = State.valueOf(stateCode).code,
                        exp = exp,
                    )
                }
            } else {
                throw ManagementException(ManagementErrorCode.POOP_CLEAN_FAIL)
            }
        }
    }

    override suspend fun graduationCheckNowSlot() {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            roomDB.slotDao().modifyIsGraduateCheckTrueByMongId(mongId = nowSlotMongId)
        }
    }

    override suspend fun graduationSlot(mongId: Long) {
        val response = managementApi.graduateMong(mongId = mongId)

        if (response.isSuccessful) {
            response.body()?.let { graduateResDto ->
                roomDB.slotDao().deleteByMongId(graduateResDto.mongId)
            }
        } else {
            throw ManagementException(ManagementErrorCode.SLEEPING_FAIL)
        }
    }

    override suspend fun getFoodHistoryNowSlot(): List<FeedHistoryVo> {
        roomDB.slotDao().findMongIdIsSelectedTrue()?.let { nowSlotMongId ->
            val buildVersion = deviceDataStore.getBuildVersion()
            val response =
                managementApi.findFeedHistory(mongId = nowSlotMongId, buildVersion = buildVersion)

            if (response.isSuccessful) {
                response.body()?.let { findFeedHistoryResDtoList ->
                    return findFeedHistoryResDtoList.map { findFeedHistoryResDto ->
                        FeedHistoryVo(
                            code = findFeedHistoryResDto.code,
                            isCanBuy = findFeedHistoryResDto.isCanBuy
                        )
                    }
                }
            }
        }

        throw CommonException(CommonErrorCode.GET_FOOD_CODE_BY_GROUP_CODE_FAIL)
    }
}