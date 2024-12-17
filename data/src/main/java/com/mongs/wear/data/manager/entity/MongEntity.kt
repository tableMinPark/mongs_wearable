package com.mongs.wear.data.manager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode
import java.time.LocalDateTime

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

    val statusCode: MongStatusCode,

    val isSleeping: Boolean,

    /* 로컬 필드 */
    val isHappy: Boolean,

    val isEating: Boolean,

    val isPoopCleaning: Boolean,

    val isSelected: Boolean,
)