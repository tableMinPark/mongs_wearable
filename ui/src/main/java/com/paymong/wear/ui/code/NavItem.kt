package com.paymong.wear.ui.code

sealed class NavItem(val route: String) {
    object MainLanding: NavItem("MAIN_LANDING")

    object Main: NavItem("MAIN")

    object Feed: NavItem("FEED")
    object FeedLanding: NavItem("FEED_LANDING")
    object Store: NavItem("STORE")

    object Activity: NavItem("ACTIVITY")
    object ActivityLanding: NavItem("ACTIVITY_LANDING")
    object Walk: NavItem("WALK")
    object Training: NavItem("TRAINING")

    object Battle: NavItem("BATTLE")
    object BattleLanding: NavItem("BATTLE_LANDING")
    object BattleConnect: NavItem("BATTLE_CONNECT")
    object BattleLoading: NavItem("BATTLE_LOADING")
    object BattleFind: NavItem("BATTLE_FIND")
    object BattleActive: NavItem("BATTLE_ACTIVE")
    object BattleSelectBefore: NavItem("BATTLE_SELECT_BEFORE")
    object BattleSelect: NavItem("BATTLE_SELECT")
    object BattleEnd: NavItem("BATTLE_END")
}
