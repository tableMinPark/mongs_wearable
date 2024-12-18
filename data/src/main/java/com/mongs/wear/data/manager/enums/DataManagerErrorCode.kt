package com.mongs.wear.data.manager.enums

import com.mongs.wear.core.enums.ErrorCode

enum class DataManagerErrorCode(
    private val message: String,
) : ErrorCode {

    MANAGER_GET_MONG_FAIL("몽 조회 실패"),
    MANAGER_CREATE_MONG_FAIL("몽 생성 실패"),
    MANAGER_DELETE_MONG_FAIL("몽 삭제 실패"),
    MANAGER_GET_FEED_ITEMS_FAIL("먹이 목록 조회 실패"),
    MANAGER_FEED_MONG_FAIL("몽 먹이 주기 실패"),
    MANAGER_GRADUATE_MONG_FAIL("몽 진화 실패"),
    MANAGER_EVOLUTION_MONG_FAIL("몽 진화 실패"),
    MANAGER_SLEEP_MONG_FAIL("몽 수면/기상 실패"),
    MANAGER_STROKE_MONG_FAIL("몽 쓰다듬기 실패"),
    MANAGER_POOP_CLEAN_MONG_FAIL("몽 쓰다듬기 실패"),
    ;

    override fun getMessage(): String {
        return this.message
    }
}