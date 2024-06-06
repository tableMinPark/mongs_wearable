package com.mongs.wear.domain.usecase.slot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.mongs.wear.domain.exception.parent.RoomException
import com.mongs.wear.domain.repositroy.SlotRepository
import com.mongs.wear.domain.vo.SlotVo
import javax.inject.Inject

class GetNowSlotUseCase @Inject constructor(
    private val slotRepository: SlotRepository
) {
    suspend operator fun invoke(): LiveData<SlotVo> {
        try {
            val slotModel = slotRepository.getNowSlotLive()
            return slotModel.map {
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
        } catch (e: RoomException) {
            return MutableLiveData(SlotVo())
        }
    }
}