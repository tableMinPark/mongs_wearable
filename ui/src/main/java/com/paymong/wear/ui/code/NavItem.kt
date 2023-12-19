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
    // 배틀
    object BattleNested: NavItem("BATTLE_NESTED")
    object Battle: NavItem("BATTLE")
    // 산책
    object WalkNested: NavItem("WALK_NESTED")
    object Walk: NavItem("WALK")
    // 훈련
    object TrainingNested: NavItem("TRAINING_NESTED")
    object Training: NavItem("TRAINING")
    // 컬렉션
    object CollectionNested: NavItem("COLLECTION_NESTED")
    object Collection: NavItem("COLLECTION")
    // 설정
    object Setting: NavItem("SETTING")
    // 개발자 페이지
    object Developer: NavItem("DEVELOPER")
    // 피드백
    object Feedback: NavItem("FEEDBACK")


//    object Activity: NavItem("ACTIVITY")
//    object ActivityLanding: NavItem("ACTIVITY_LANDING")
//    object Walk: NavItem("WALK")
//    object Training: NavItem("TRAINING")

//    object Battle: NavItem("BATTLE")
//    object BattleLanding: NavItem("BATTLE_LANDING")
//    object BattleConnect: NavItem("BATTLE_CONNECT")
//    object BattleLoading: NavItem("BATTLE_LOADING")
//    object BattleFind: NavItem("BATTLE_FIND")
//    object BattleActive: NavItem("BATTLE_ACTIVE")
//    object BattleSelectBefore: NavItem("BATTLE_SELECT_BEFORE")
//    object BattleSelect: NavItem("BATTLE_SELECT")
//    object BattleEnd: NavItem("BATTLE_END")

}
