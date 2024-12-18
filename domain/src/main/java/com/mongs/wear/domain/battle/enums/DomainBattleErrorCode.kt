package com.mongs.wear.domain.battle.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DomainBattleErrorCode(
    private val message: String,
) : ErrorCode {

    ;

    override fun getMessage(): String {
        return this.message
    }
}