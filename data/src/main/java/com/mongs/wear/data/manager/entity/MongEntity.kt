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
    var isCurrent: Boolean,

    var graduateCheck: Boolean,

    var isHappy: Boolean,

    var isEating: Boolean,

    var isPoopCleaning: Boolean,

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
}