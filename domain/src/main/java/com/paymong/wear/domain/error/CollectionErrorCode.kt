package com.paymong.wear.domain.error

enum class CollectionErrorCode(val message: String) : ErrorCode {
    GET_COLLECTION_MAP_FAIL("맵 컬렉션 조회 실패"),
    GET_COLLECTION_MONG_FAIL("몽 컬렉션 조회 실패"),
    ;
    override fun message(): String {
        return message
    }
}