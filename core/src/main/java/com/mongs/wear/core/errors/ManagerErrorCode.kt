package com.mongs.wear.core.errors

enum class ManagerErrorCode(
    private val message: String,
) : ErrorCode {

    DATA_MANAGER_GET_MONG("몽 조회 실패"),
    DATA_MANAGER_CREATE_MONG("몽 생성에 실패했습니다."),
    DATA_MANAGER_DELETE_MONG("몽 삭제에 실패했습니다."),
    DATA_MANAGER_GET_FEED_ITEMS("먹이 목록 조회에 실패했습니다."),
    DATA_MANAGER_FEED_MONG("몽 먹이 주기에 실패했습니다."),
    DATA_MANAGER_GRADUATE_MONG("몽 진화에 실패했습니다."),
    DATA_MANAGER_EVOLUTION_MONG("몽 진화에 실패했습니다."),
    DATA_MANAGER_SLEEP_MONG("몽 수면/기상에 실패했습니다."),
    DATA_MANAGER_STROKE_MONG("몽 쓰다듬기에 실패했습니다."),
    DATA_MANAGER_POOP_CLEAN_MONG("몽 배변처리에 실패했습니다."),
    ;

    override fun getMessage(): String {
        return this.message
    }
}