package com.paymong.wear.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "mong")
data class Mong(
    @PrimaryKey(autoGenerate = true)
    var slotId: Long = 0L,
    var mongId: Long = -1L,
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
//    companion object {
//        fun of(mongResModel: MongResModel): Mong {
//            return Mong(
//                mongId = mongResModel.mongId,
//                born = mongResModel.born,
//                weight = mongResModel.weight,
//                mongCode = mongResModel.mongCode,
//                nextMongCode = mongResModel.nextMongCode,
//                stateCode = mongResModel.stateCode,
//                nextStateCode = mongResModel.nextStateCode,
//                poopCount = mongResModel.poopCount,
//                health = mongResModel.health,
//                satiety = mongResModel.satiety,
//                strength = mongResModel.strength,
//                sleep = mongResModel.sleep
//            )
//        }
//    }
}
