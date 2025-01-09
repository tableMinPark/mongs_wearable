package com.mongs.wear.data.manager.resolver

import androidx.room.Transaction
import com.mongs.wear.data.global.room.RoomDB
import com.mongs.wear.data.manager.dto.response.GetMongResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManagementObserveResolver @Inject constructor(
    private val roomDB: RoomDB,
) {

    @Transaction
    fun updateMong(getMongResponseDto: GetMongResponseDto) {

        roomDB.mongDao().findByMongId(mongId = getMongResponseDto.mongId)?.let { mongEntity ->
            roomDB.mongDao().save(
                mongEntity.update(
                    mongName = getMongResponseDto.mongName,
                    mongTypeCode = getMongResponseDto.mongTypeCode,
                    payPoint = getMongResponseDto.payPoint,
                    stateCode = getMongResponseDto.stateCode,
                    isSleeping = getMongResponseDto.isSleep,
                    statusCode = getMongResponseDto.statusCode,
                    expRatio = getMongResponseDto.expRatio,
                    weight = getMongResponseDto.weight,
                    healthyRatio = getMongResponseDto.healthyRatio,
                    satietyRatio = getMongResponseDto.satietyRatio,
                    strengthRatio = getMongResponseDto.strengthRatio,
                    fatigueRatio = getMongResponseDto.fatigueRatio,
                    poopCount = getMongResponseDto.poopCount,
                    updatedAt = getMongResponseDto.updatedAt,
                )
            )
        }
    }
}