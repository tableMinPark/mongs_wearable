package com.mongs.wear.presentation.layout

//import com.mongs.wear.presentation.pages.battle.match.BattleMatchView
//import com.mongs.wear.presentation.pages.battle.menu.BattleMenuView
//import com.mongs.wear.presentation.pages.collection.map.CollectionMapPickView
//import com.mongs.wear.presentation.pages.collection.menu.CollectionMenuView
//import com.mongs.wear.presentation.pages.collection.mong.CollectionMongPickView
//import com.mongs.wear.presentation.pages.feed.food.FeedFoodPickView
//import com.mongs.wear.presentation.pages.feed.menu.FeedMenuView
//import com.mongs.wear.presentation.pages.feed.snack.FeedSnackPickView
//import com.mongs.wear.presentation.pages.feedback.FeedbackView
//import com.mongs.wear.presentation.pages.help.menu.HelpMenuView
//import com.mongs.wear.presentation.pages.payment.chargeStartPoint.PaymentChargeStarPointView
//import com.mongs.wear.presentation.pages.payment.exchangePayPoint.PaymentExchangePayPointView
//import com.mongs.wear.presentation.pages.payment.menu.PaymentMenuView
//import com.mongs.wear.presentation.pages.slot.SlotPickView
//import com.mongs.wear.presentation.pages.training.jumping.TrainingJumpingView
//import com.mongs.wear.presentation.pages.training.menu.TrainingMenuView
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.DAL_MU_RI
import com.mongs.wear.presentation.assets.MongsWhite
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.common.BaseViewModel
import com.mongs.wear.presentation.component.background.MainBackground
import com.mongs.wear.presentation.component.background.ServerErrorBackground
import com.mongs.wear.presentation.component.button.BlueButton
import com.mongs.wear.presentation.component.common.LoadingBar
import com.mongs.wear.presentation.pages.login.LoginView
import com.mongs.wear.presentation.pages.main.layout.MainPagerView
import kotlinx.coroutines.launch


@Composable
fun MainView (
    closeApp: () -> Unit,
    context: Context = LocalContext.current,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val networkFlag = mainViewModel.network.observeAsState(true)

    if (mainViewModel.uiState.loadingBar) {
        MainBackground()
        MainLoadingBar()
    } else if (!networkFlag.value) {
        ServerErrorBackground()
        NetworkErrorContent(closeApp = closeApp, modifier = Modifier.zIndex(1f))
    } else {
        NavContent(closeApp = closeApp)
    }

    /**
     * 예외 발생 시 에러 Toast
     */
    LaunchedEffect(BaseViewModel.errorToast) {
        if (BaseViewModel.errorToast) {

            BaseViewModel.errorToast = false

            Toast.makeText(
                context,
                BaseViewModel.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun NetworkErrorContent (
    closeApp: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Image(
                painter = painterResource(R.drawable.logo_not_open),
                contentDescription = null,
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "서버연결실패\n재실행 해주세요",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = MongsWhite,
            )

            Spacer(modifier = Modifier.height(25.dp))

            BlueButton(
                text =  "앱종료",
                onClick = closeApp,
            )
        }
    }
}

/**
 * 라우터
 */
@Composable
fun NavContent(
    closeApp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(initialPage = 2) { 5 }

    val pagerScroll = fun (page: Int) {
        coroutineScope.launch {
            pagerState.scrollToPage(page)
        }
    }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.Login.route
    ) {
        composable(route = NavItem.Login.route) {
            LoginView(
                navController = navController,
                closeApp = closeApp,
            )
        }

        composable(route = NavItem.MainPager.route) {
            MainPagerView(
                navController = navController,
                pagerState = pagerState,
                pagerScroll = pagerScroll,
            )
        }

//        navigation(
//            startDestination = NavItem.FeedMenu.route,
//            route = NavItem.FeedNested.route
//        ) {
//            composable(route = NavItem.FeedMenu.route) {
//                FeedMenuView(navController = navController)
//            }
//            composable(route = NavItem.FeedFoodPick.route) {
//                FeedFoodPickView(navController = navController, scrollPage = pagerScroll)
//            }
//            composable(route = NavItem.FeedSnackPick.route) {
//                FeedSnackPickView(navController = navController, scrollPage = pagerScroll)
//            }
//        }
//
//        navigation(
//            startDestination = NavItem.CollectionMenu.route,
//            route = NavItem.CollectionNested.route
//        ) {
//            composable(route = NavItem.CollectionMenu.route) {
//                CollectionMenuView(navController = navController)
//            }
//            composable(route = NavItem.CollectionMapPick.route) {
//                CollectionMapPickView(navController = navController)
//            }
//            composable(route = NavItem.CollectionMongPick.route) {
//                CollectionMongPickView(navController = navController)
//            }
//        }
//
//        composable(route = NavItem.SlotPick.route) {
//            SlotPickView(navController = navController, scrollPage = pagerScroll)
//        }
//
//        navigation(
//            startDestination = NavItem.PaymentMenu.route,
//            route = NavItem.PaymentNested.route
//        ) {
//            composable(route = NavItem.PaymentMenu.route) {
//                PaymentMenuView(navController = navController)
//            }
//            composable(route = NavItem.PaymentChargeStarPoint.route) {
//                PaymentChargeStarPointView(navController = navController)
//            }
//            composable(route = NavItem.PaymentExchangePayPoint.route) {
//                PaymentExchangePayPointView(navController = navController)
//            }
//        }
//
//        composable(route = NavItem.Feedback.route) {
//            FeedbackView()
//        }
//
//        navigation(
//            startDestination = NavItem.TrainingMenu.route,
//            route = NavItem.TrainingNested.route
//        ) {
//            composable(route = NavItem.TrainingMenu.route) {
//                TrainingMenuView(navController = navController)
//            }
//            composable(route = NavItem.TrainingJumping.route) {
//                TrainingJumpingView(navController = navController)
//            }
//        }
//
//        navigation(
//            startDestination = NavItem.BattleMenu.route,
//            route = NavItem.BattleNested.route
//        ) {
//            composable(route = NavItem.BattleMenu.route) {
//                BattleMenuView(navController = navController)
//            }
//            composable(route = NavItem.BattleMatch.route) {
//                BattleMatchView(navController = navController)
//            }
//        }
//
//        navigation(
//            startDestination = NavItem.HelpMenu.route,
//            route = NavItem.HelpNested.route
//        ) {
//            composable(route = NavItem.HelpMenu.route) {
//                HelpMenuView()
//            }
//        }
    }
}


@Composable
private fun MainLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingBar()
    }
}
