package com.mongs.wear.ui.global.resource

sealed class NavItem(val route: String) {
    /* Login */
    object Login: NavItem("Login")
    /* MainPager */
    object MainPager: NavItem("MainPager")
    /* Feed */
    object FeedNested: NavItem("FeedNested")
    object FeedMenu: NavItem("FeedMenu")
    object FeedFoodPick: NavItem("FeedFoodPick")
    object FeedSnackPick: NavItem("FeedSnackPick")
    /* Collection */
    object CollectionNested: NavItem("CollectionNested")
    object CollectionMenu: NavItem("CollectionMenu")
    object CollectionMapPick: NavItem("CollectionMapPick")
    object CollectionMongPick: NavItem("CollectionMongPick")
    /* SlotPick */
    object SlotPick: NavItem("SlotPick")
    /* Payment */
    object PaymentNested: NavItem("PaymentNested")
    object PaymentMenu: NavItem("PaymentMenu")
    object PaymentChargeStarPoint: NavItem("PaymentChargeStarPoint")
    object PaymentExchangePayPoint: NavItem("PaymentExchangePayPoint")
    /* Feedback */
    object Feedback: NavItem("Feedback")
    /* Training */
    object TrainingNested: NavItem("TrainingNested")
    object TrainingMenu: NavItem("TrainingMenu")
    object TrainingJumping: NavItem("TrainingJumping")

    /* Battle */
    object BattleNested: NavItem("BattleNested")
    object BattleMenu: NavItem("BattleMenu")
    object BattleMatch: NavItem("BattleMatch")

    /* Help */
    object HelpNested: NavItem("HelpNested")
    object HelpMenu: NavItem("HelpMenu")
}
