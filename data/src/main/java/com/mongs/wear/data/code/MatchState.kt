package com.mongs.wear.data.code

import com.mongs.wear.domain.code.MatchStateCode

enum class MatchState(
    val code: MatchStateCode
) {
    NONE(MatchStateCode.NONE),
    ENTER(MatchStateCode.ENTER),
    MATCH(MatchStateCode.MATCH),
    PICK(MatchStateCode.PICK),
    OVER(MatchStateCode.OVER),
}