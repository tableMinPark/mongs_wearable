package com.mongs.wear.data_.manager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.data_.manager.enums.MongStateCode
import dagger.Component.Builder
import java.time.LocalDateTime

@Builder
@Entity(tableName = "mongs_mong")
data class MongEntity(

    @PrimaryKey
    val mongId: Long,

    val mongName: String,

    val payPoint: Int,

    val mongTypeCode: String,

    val createdAt: LocalDateTime,

    val expRatio: Double,

    val weightRatio: Double,

    val healthyRatio: Double,

    val satietyRatio: Double,

    val strengthRatio: Double,

    val fatigueRatio: Double,

    val poopCount: Int,

    val stateCode: MongStateCode,

    val statusCode: com.mongs.wear.data_.manager.enums.MongStatusCode,

    val isSleeping: Boolean,

    /* 로컬 필드 */
    val isHappy: Boolean,

    val isEating: Boolean,

    val isPoopCleaning: Boolean,

    val isSelected: Boolean,
)