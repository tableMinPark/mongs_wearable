package com.mongs.wear.core.errors

enum class ActivityErrorCode(
    private val message: String,
) : ErrorCode {

    DATA_ACTIVITY_NOT_EXISTS_MATCH("배틀 매칭이 존재하지 않음"),
    DATA_ACTIVITY_NOT_EXISTS_MATCH_PLAYER("배틀 플레이어가 존재하지 않음"),
    DATA_ACTIVITY_CREATE_MATCH("배틀 매칭 등록 실패"),
    DATA_ACTIVITY_DELETE_MATCH("배틀 매칭 삭제 실패"),
    DATA_ACTIVITY_UPDATE_OVER_MATCH("배틀 매치 결과 조회 실패"),
    DATA_ACTIVITY_ENTER_MATCH("배틀 매치 입장 실패"),
    DATA_ACTIVITY_PICK_MATCH("배틀 매치 선택 실패"),
    DATA_ACTIVITY_EXIT_MATCH("배틀 매치 퇴장 실패"),
    ;

    override fun getMessage(): String {
        return this.message
    }
}