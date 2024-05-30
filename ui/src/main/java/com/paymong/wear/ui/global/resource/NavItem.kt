package com.paymong.wear.ui.global.resource

sealed class NavItem(val route: String) {
    object Login: NavItem("Login")
    object MainPager: NavItem("MainPager")
}
