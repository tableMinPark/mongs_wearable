package com.mongs.wear.data.manager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode
import com.mongs.wear.domain.management.model.MongModel
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "mongs_mong")
data class MongEntity(

    @PrimaryKey
    val mongId: Long = 0L,

    var mongName: String,

    var payPoint: Int,

    var mongTypeCode: String,

    val createdAt: LocalDateTime,

    var weight: Double,

    var expRatio: Double,

    var healthyRatio: Double,

    var satietyRatio: Double,

    var strengthRatio: Double,

    var fatigueRatio: Double,

    var poopCount: Int,

    var stateCode: MongStateCode,

    var statusCode: MongStatusCode,

    var isSleeping: Boolean,

    /* 로컬 필드 */
    var isCurrent: Boolean = false,

    var graduateCheck: Boolean = false,

    /* 무결성 필드 */
    var updatedAt: LocalDateTime,
) {

    fun update(
        mongName: String = this.mongName,
        mongTypeCode: String = this.mongTypeCode,
        payPoint: Int = this.payPoint,
        stateCode: MongStateCode = this.stateCode,
        isSleeping: Boolean = this.isSleeping,
        statusCode: MongStatusCode = this.statusCode,
        weight: Double = this.weight,
        expRatio: Double = this.expRatio,
        healthyRatio: Double = this.healthyRatio,
        satietyRatio: Double = this.satietyRatio,
        strengthRatio: Double = this.strengthRatio,
        fatigueRatio: Double = this.fatigueRatio,
        poopCount: Int = this.poopCount,
        isCurrent: Boolean = this.isCurrent,
        updatedAt: LocalDateTime = this.updatedAt,

    ) : MongEntity {

        this.mongName = mongName
        this.mongTypeCode = mongTypeCode
        this.payPoint = payPoint
        this.stateCode = stateCode
        this.isSleeping = isSleeping
        this.statusCode = statusCode
        this.weight = weight
        this.expRatio = expRatio
        this.healthyRatio = healthyRatio
        this.satietyRatio = satietyRatio
        this.strengthRatio = strengthRatio
        this.fatigueRatio = fatigueRatio
        this.poopCount = poopCount
        this.isCurrent = isCurrent
        this.updatedAt = updatedAt

        return this
    }

    fun graduateCheck() {
        this.graduateCheck = true
    }

    fun toMongModel() = MongModel(
        mongId = this.mongId,
        mongName = this.mongName,
        payPoint = this.payPoint,
        mongTypeCode = this.mongTypeCode,
        createdAt = this.createdAt,
        weight = this.weight,
        expRatio = this.expRatio,
        healthyRatio = this.healthyRatio,
        satietyRatio = this.satietyRatio,
        strengthRatio = this.strengthRatio,
        fatigueRatio = this.fatigueRatio,
        poopCount = this.poopCount,
        stateCode = this.stateCode,
        statusCode = this.statusCode,
        isSleeping = this.isSleeping,
        isCurrent = this.isCurrent,
        graduateCheck = this.graduateCheck,
    )
}