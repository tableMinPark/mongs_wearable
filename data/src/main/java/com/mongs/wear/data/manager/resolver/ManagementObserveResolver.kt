package com.mongs.wear.data.manager.resolver

import androidx.room.Transaction
import com.mongs.wear.data.global.room.RoomDB
import com.mongs.wear.data.manager.dto.response.GetMongResponseDto
import com.mongs.wear.data.manager.dto.response.MongResponseDto
import com.mongs.wear.data.manager.dto.response.MongStateResponseDto
import com.mongs.wear.data.manager.dto.response.MongStatusResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManagementObserveResolver @Inject constructor(
    private val roomDB: RoomDB,
) {

    @Transaction
    fun updateMong(getMongResponseDto: GetMongResponseDto) {

        roomDB.mongDao().findByMongId(mongId = getMongResponseDto.mong.mongId)?.let { mongEntity ->

            roomDB.mongDao().save(
                mongEntity.update(
//                                    mongId = body.result.mong.mongId,
                    mongName = getMongResponseDto.mong.mongName,
                    mongTypeCode = getMongResponseDto.mong.mongTypeCode,
                    payPoint = getMongResponseDto.mong.payPoint,
//                                    createdAt = body.result.mong.createdAt,

                    stateCode = getMongResponseDto.mongState.stateCode,
                    isSleeping = getMongResponseDto.mongState.isSleep,

                    statusCode = getMongResponseDto.mongStatus.statusCode,
                    weight = getMongResponseDto.mongStatus.weight,
                    expRatio = getMongResponseDto.mongStatus.expRatio,
                    healthyRatio = getMongResponseDto.mongStatus.healthyRatio,
                    satietyRatio = getMongResponseDto.mongStatus.satietyRatio,
                    strengthRatio = getMongResponseDto.mongStatus.strengthRatio,
                    fatigueRatio = getMongResponseDto.mongStatus.fatigueRatio,
                    poopCount = getMongResponseDto.mongStatus.poopCount,

                    updatedAt = getMongResponseDto.mong.updatedAt,
                )
            )
        }
    }
}