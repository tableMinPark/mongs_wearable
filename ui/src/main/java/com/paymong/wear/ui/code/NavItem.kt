package com.paymong.wear.ui.code

sealed class NavItem(val route: String) {
    // 메인
    object MainNested: NavItem("MAIN_NESTED")
    // 상점
    // 산책
    object WalkNested: NavItem("WALK_NESTED")
    object Walk: NavItem("WALK")
    // 훈련
    // 맵 컬렉션
    object MapCollectionNested: NavItem("MAP_COLLECTION_NESTED")
    object MapCollection: NavItem("MAP_COLLECTION")
    // 몽 컬렉션
    object MongCollectionNested: NavItem("MONG_COLLECTION_NESTED")
    object MongCollection: NavItem("MONG_COLLECTION")



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
    object TrainingLanding: NavItem("TRAINING_LANDING")
    object Training: NavItem("TRAINING")
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
    /** 맵 수집 **/
    object CollectMap: NavItem("COLLECT_MAP")
    /** 설정 **/
    object Setting: NavItem("SETTING")
    /** 개발자 페이지 **/
    object Developer: NavItem("DEVELOPER")
    /** 피드백 **/
    object Feedback: NavItem("FEEDBACK")
}
