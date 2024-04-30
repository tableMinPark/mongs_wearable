package com.paymong.wear.ui.global.resource

sealed class NavItem(val route: String) {
    /** 앱 시작 로딩 **/
    object InitLanding: NavItem("INIT_LANDING")
    /** 메인 **/
    object Main: NavItem("MAIN")
    /** 슬롯 선택 **/
    object SlotSelect: NavItem("SLOT_SELECT")
    /** 상점 **/
    object FeedNested: NavItem("FEED_NESTED")
    object FeedMenu: NavItem("FEED_MENU")
    object FeedSelect: NavItem("FEED_SELECT")
    /** 훈련 **/
    object TrainingNested: NavItem("TRAINING_NESTED")
    object TrainingSelect: NavItem("TRAINING_SELECT")
    object TrainingJumping: NavItem("TRAINING_JUMPING")
    /** 배틀 **/
    object BattleNested: NavItem("BATTLE_NESTED")
    object BattleLanding: NavItem("BATTLE_LANDING")
    object Battle: NavItem("BATTLE")
    /** 컬렉션 **/
    object CollectionNested: NavItem("COLLECTION_NESTED")
    object CollectionMenu: NavItem("COLLECTION_MENU")
    object CollectionSelect: NavItem("COLLECTION_SELECT")
    object CollectionMong: NavItem("COLLECTION_MONG")
    object CollectionMap: NavItem("COLLECTION_MAP")
    /** 스타 포인트 충전 **/
    object ChargeNested: NavItem("CHARGE_NESTED")
    object ChargeMenu: NavItem("CHARGE_MENU")
    object ChargeStarPoint: NavItem("CHARGE_STAR_POINT")
    object ChargePayPoint: NavItem("CHARGE_PAY_POINT")
    /** 맵 수집 **/
    object MapSearch: NavItem("MAP_SEARCH")
    /** 설정 **/
    object Setting: NavItem("SETTING")
    /** 레퍼 페이지 **/
    object Reference: NavItem("REFERENCE")
    /** 피드백 **/
    object Feedback: NavItem("FEEDBACK")
}
