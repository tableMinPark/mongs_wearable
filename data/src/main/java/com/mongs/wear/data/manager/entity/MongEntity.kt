package com.mongs.wear.data.manager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.core.enums.MongStatusCode
import com.mongs.wear.domain.management.model.MongModel
import java.time.LocalDateTime

@Entity(tableName = "mongs_mong")
data class MongEntity(

    @PrimaryKey
    val mongId: Long = 0L,

    val mongName: String,

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

    var isHappy: Boolean = false,

    var isEating: Boolean = false,

    var isPoopCleaning: Boolean = false,

) {

    fun update(
        payPoint: Int = this.payPoint,
        mongTypeCode: String = this.mongTypeCode,
        weight: Double = this.weight,
        expRatio: Double = this.expRatio,
        healthyRatio: Double = this.healthyRatio,
        satietyRatio: Double = this.satietyRatio,
        strengthRatio: Double = this.strengthRatio,
        fatigueRatio: Double = this.fatigueRatio,
        poopCount: Int = this.poopCount,
        stateCode: MongStateCode = this.stateCode,
        statusCode: MongStatusCode = this.statusCode,
        isSleeping: Boolean = this.isSleeping,
        isCurrent: Boolean = this.isCurrent,
    ) : MongEntity {

        this.payPoint = payPoint
        this.mongTypeCode = mongTypeCode
        this.expRatio = expRatio
        this.weight = weight
        this.healthyRatio = healthyRatio
        this.satietyRatio = satietyRatio
        this.strengthRatio = strengthRatio
        this.fatigueRatio = fatigueRatio
        this.poopCount = poopCount
        this.stateCode = stateCode
        this.statusCode = statusCode
        this.isSleeping = isSleeping
        this.isCurrent = isCurrent

        return this
    }

    fun graduateCheck() {
        this.graduateCheck = true
    }

    fun happy() {
        this.isHappy = true
    }

    fun eat() {
        this.isEating = true
    }

    fun poopClean() {
        this.isPoopCleaning = true
    }

    fun resetFlag() {
        this.isHappy = false
        this.isEating = false
        this.isPoopCleaning = false
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
        isHappy = this.isHappy,
        isEating = this.isEating,
        isPoopCleaning = this.isPoopCleaning,
    )
}