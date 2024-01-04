package com.paymong.wear.ui.code

sealed class NavItem(val route: String) {
    // 앱 시작 로딩
    object InitLanding: NavItem("INIT_LANDING")
    // 메인
    object MainNested: NavItem("MAIN_NESTED")
    object Main: NavItem("MAIN")
    object SlotSelect: NavItem("SLOT_SELECT")
    // 상점
    object FeedNested: NavItem("FEED_NESTED")
    object Feed: NavItem("FEED")
    object FeedSelect: NavItem("FEED_SELECT")
    // 산책
    object WalkNested: NavItem("WALK_NESTED")
    object Walk: NavItem("WALK")
    // 훈련
    object TrainingNested: NavItem("TRAINING_NESTED")
    object Training: NavItem("TRAINING")
    // 맵 컬렉션
    object MapCollectionNested: NavItem("MAP_COLLECTION_NESTED")
    object MapCollection: NavItem("MAP_COLLECTION")
    // 몽 컬렉션
    object MongCollectionNested: NavItem("MONG_COLLECTION_NESTED")
    object MongCollection: NavItem("MONG_COLLECTION")
    // 설정
    object Setting: NavItem("SETTING")
    // 개발자 페이지
    object Developer: NavItem("DEVELOPER")
    // 피드백
    object Feedback: NavItem("FEEDBACK")
}
