package com.paymong.wear.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paymong.wear.data.api.model.response.MongResModel
import java.time.LocalDateTime

@Entity(tableName = "mong")
data class Mong(
    @PrimaryKey
    var slotId: Int,
    var isSlotEmpty: Boolean = true,
    var mongId: Long = -1L,

    var mongName: String = "이름 없음",
    var born: LocalDateTime = LocalDateTime.now(),
    var weight: Int = 0,
    var mongCode: String = "CH444",
    var nextMongCode: String = "CH444",

    var stateCode: String = "CD444",
    var nextStateCode: String = "CD444",
    var poopCount: Int = 0,

    var health: Float = 0f,
    var satiety: Float = 0f,
    var strength: Float = 0f,
    var sleep: Float = 0f,
) {
    companion object {
        fun of(mongResModel: MongResModel): Mong {
            return Mong(
                slotId = mongResModel.slotId,
                isSlotEmpty = false,
                mongId = mongResModel.mongId,
                mongName = mongResModel.mongName,
                born = mongResModel.born,
                weight = mongResModel.weight,
                mongCode = mongResModel.mongCode,
                nextMongCode = mongResModel.nextMongCode,
                stateCode = mongResModel.stateCode,
                nextStateCode = mongResModel.nextStateCode,
                poopCount = mongResModel.poopCount,
                health = mongResModel.health,
                satiety = mongResModel.satiety,
                strength = mongResModel.strength,
                sleep = mongResModel.sleep
            )
        }
    }
}
