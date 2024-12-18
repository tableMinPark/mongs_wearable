package com.mongs.wear.data.auth.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DataActivityErrorCode(
    private val message: String,
) : ErrorCode {

    ACTIVITY_NOT_EXISTS_MATCH("배틀 매칭이 존재하지 않음"),
    ACTIVITY_NOT_EXISTS_MATCH_PLAYER("배틀 플레이어가 존재하지 않음"),

    ACTIVITY_CREATE_MATCH_FAIL("배틀 매칭 등록 실패"),
    ACTIVITY_DELETE_MATCH_FAIL("배틀 매칭 삭제 실패"),
    ACTIVITY_UPDATE_OVER_MATCH_FAIL("배틀 매치 결과 조회 실패"),
    ACTIVITY_ENTER_MATCH_FAIL("배틀 매치 입장 실패"),
    ACTIVITY_PICK_MATCH_FAIL("배틀 매치 선택 실패"),
    ACTIVITY_EXIT_MATCH_FAIL("배틀 매치 퇴장 실패"),

    ;

    override fun getMessage(): String {
        return this.message
    }
}