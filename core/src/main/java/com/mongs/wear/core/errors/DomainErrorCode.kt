package com.mongs.wear.core.errors

enum class DomainErrorCode(
    private val message: String,
    private val isMessageShow: Boolean,
) : ErrorCode {

    // Global
    DOMAIN_GLOBAL_UNKNOWN_ERROR("알 수 없는 에러 입니다.", false),
    DOMAIN_GLOBAL_DATA_ERROR("Data layer 에러 입니다.", false),

    // Auth
    DOMAIN_AUTH_NOT_EXISTS_GOOGLE_ACCOUNT_ID("구글 계정 정보가 존재하지 않습니다.", false),
    DOMAIN_AUTH_NOT_EXISTS_EMAIL("이메일이 존재하지 않습니다.", false),
    DOMAIN_AUTH_NOT_EXISTS_NAME("이름이 존재하지 않습니다.", false),
    DOMAIN_AUTH_JOIN_FAILED("회원가입에 실패하였습니다.", false),
    DOMAIN_AUTH_LOGIN_FAILED("로그인에 실패하였습니다.", false),
    DOMAIN_AUTH_LOGOUT_FAILED("로그아웃에 실패하였습니다.", false),
    DOMAIN_AUTH_NEED_JOIN_FAILED("회원가입이 필요합니다.", false),
    DOMAIN_AUTH_NEED_UPDATE_APP_FAILED("앱 업데이트가 필요합니다.", false),

    // Battle
    DOMAIN_BATTLE_EXIT_MATCH_FAILED("매치 퇴장에 실패하였습니다.", false),
    DOMAIN_BATTLE_GET_MATCH_FAILED("매치 정보를 가져오는데 실패하였습니다.", false),
    DOMAIN_BATTLE_GET_MY_MATCH_PLAYER_FAILED("내 매치 정보를 가져오는데 실패하였습니다.", false),
    DOMAIN_BATTLE_GET_RIVER_MATCH_PLAYER_FAILED("상대 매치 정보를 가져오는데 실패하였습니다.", false),
    DOMAIN_BATTLE_MATCH_START_FAILED("매치 시작에 실패하였습니다.", false),
    DOMAIN_BATTLE_MATCH_WAIT_CANCEL_FAILED("매치 대기 취소에 실패하였습니다.", false),
    DOMAIN_BATTLE_MATCH_WAIT_FAILED("매치 대기에 실패하였습니다.", false),
    DOMAIN_BATTLE_OVER_MATCH_FAILED("매치 종료에 실패하였습니다.", false),
    DOMAIN_BATTLE_PICK_MATCH_FAILED("매치 선택에 실패하였습니다.", false),

    //Collection
    DOMAIN_COLLECTION_GET_MAP_COLLECTIONS_FAILED("맵 컬렉션을 가져오는데 실패하였습니다.", false),
    DOMAIN_COLLECTION_GET_MONG_COLLECTIONS_FAILED("몽 컬렉션을 가져오는데 실패하였습니다.", false),

    // Device
    DOMAIN_DEVICE_CONNECT_MQTT_FAILED("MQTT 연결에 실패하였습니다.", false),
    DOMAIN_DEVICE_GET_BACKGROUND_MAP_CODE_FAILED("배경 맵 코드를 가져오는데 실패하였습니다.", false),
    DOMAIN_DEVICE_GET_NETWORK_FAILED("네트워크 플래그를 가져오는데 실패하였습니다.", false),
    DOMAIN_DEVICE_SET_BACKGROUND_MAP_CODE_FAILED("배경 맵 코드를 설정하는데 실패하였습니다.", false),
    DOMAIN_DEVICE_SET_NETWORK_FAILED("네트워크 플래그를 설정하는데 실패하였습니다.", false),

    // Feedback
    DOMAIN_FEEDBACK_CREATE_FEEDBACK_FAILED("피드백을 생성하는데 실패하였습니다.", false),

    // Management
    DOMAIN_MANAGEMENT_CREATE_MONG_FAILED("몽을 생성하는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_DELETE_MONG_FAILED("몽을 삭제하는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_EVOLUTION_MONG_FAILED("몽을 성장시키는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_FEED_MONG_FAILED("몽 밥먹이기에 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_GET_CURRENT_SLOT_FAILED("현재 슬롯을 가져오는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_GET_FOOD_CODES_FAILED("음식 코드를 가져오는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_GET_SLOTS_FAILED("슬롯을 가져오는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_SNACK_CODES_FAILED("스낵 코드를 가져오는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_GRADUATE_CHECK_MONG_FAILED("몽 졸업 체크에 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_GRADUATE_MONG_FAILED("몽을 졸업시키는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_POOP_CLEAN_MONG_FAILED("몽 배변 처리에 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_SET_CURRENT_SLOT_FAILED("현재 슬롯을 설정하는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_SLEEP_MONG_FAILED("몽을 재우는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_STROKE_MONG_FAILED("몽을 쓰다듬는데 실패하였습니다.", false),
    DOMAIN_MANAGEMENT_TRAINING_MONG_FAILED("몽을 훈련시키는데 실패하였습니다.", false),

    // Player
    DOMAIN_PLAYER_BUY_SLOT_FAILED("슬롯을 구매하는데 실패하였습니다.", false),
    DOMAIN_PLAYER_EXCHANGE_WALKING_COUNT_FAILED("걸음 수를 환전하는데 실패하였습니다.", false),
    DOMAIN_PLAYER_GET_SLOT_COUNT_FAILED("슬롯 개수를 가져오는데 실패하였습니다.", false),
    DOMAIN_PLAYER_GET_STAR_POINT_FAILED("스타 포인트를 가져오는데 실패하였습니다.", false),
    DOMAIN_GET_STEPS_FAILED("걸음 수를 가져오는데 실패하였습니다.", false),
    DOMAIN_SET_TOTAL_WALKING_COUNT_FAILED("총 걸음 수를 가져오는데 실패하였습니다.", false),
    DOMAIN_SYNC_TOTAL_WALKING_COUNT_FAILED("총 걸음 수를 동기화하는데 실패하였습니다.", false),

    // Store
    DOMAIN_CONSUME_PRODUCT_ORDER_FAILED("상품 주문을 소비하는데 실패하였습니다.", false),
    DOMAIN_GET_CONSUMED_ORDER_IDS_FAILED("소비 된 주문 ID 를 조회하는데 실패하였습니다.", false),
    DOMAIN_GET_PRODUCT_IDS_FAILED("상품 ID 를 조회하는데 실패하였습니다.", false),
    ;

    override fun getMessage(): String {
        return this.message
    }

    override fun isMessageShow(): Boolean {
        return this.isMessageShow
    }
}