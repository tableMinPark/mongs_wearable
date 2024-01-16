package com.paymong.wear.ui.view.feed

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import com.paymong.wear.domain.model.FeedModel
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.FeedSelectCode
import com.paymong.wear.domain.viewModel.feed.FeedSelectViewModel
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.common.background.FeedSelectBackGround
import kotlin.math.max
import kotlin.math.min

@Composable
fun FeedSelectView(
    navController: NavController,
    code: String,
    feedSelectViewModel: FeedSelectViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    /** Flag **/
    val processCode = feedSelectViewModel.processCode.observeAsState(FeedSelectCode.LOAD_FEED_LIST)

    /** Observer **/
    val payPoint = feedSelectViewModel.payPoint.observeAsState(DefaultValue.payPoint)
    val feedList =
        if (code == "FD")
            feedSelectViewModel.foodList.observeAsState(ArrayList())
        else
            feedSelectViewModel.snackList.observeAsState(ArrayList())

    /** Background **/
    FeedSelectBackGround()

    /** Logic by ProcessCode **/
    when(processCode.value) {
        FeedSelectCode.LOAD_FEED_LIST -> {
            FeedSelectProcess()
        }
        else -> {
            FeedSelectContent(
                payPoint = payPoint,
                cantFeeding = {
                    Toast.makeText(context, "포인트가 부족합니다!", Toast.LENGTH_SHORT).show()
                },
                feeding = { feedCode ->
                    feedSelectViewModel.feeding(feedCode)
                    navController.popBackStack(route = NavItem.FeedNested.route, inclusive = true)
                },
                feedList = feedList
            )
        }
    }
}

@Composable
fun FeedSelectContent(
    payPoint: State<Int>,
    feeding: (String) -> Unit,
    cantFeeding: () -> Unit,
    feedList: State<List<FeedModel>>
) {
    val nowIndex = remember { mutableIntStateOf(0) }
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = nowIndex.intValue
            override val pageCount: Int
                get() = feedList.value.size
        }
    }
    val nowFeed = feedList.value[nowIndex.intValue]
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .zIndex(0f)
                .padding(top = 7.dp, bottom = 23.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                PayPointItem(payPoint = payPoint)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                FeedItem(name = nowFeed.name, code = nowFeed.code)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                if(nowFeed.price <= payPoint.value) {
                    FeedSelectEnableButton(
                        onClick = feeding,
                        code = nowFeed.code,
                        price = nowFeed.price
                    )
                } else {
                    FeedSelectDisableButton(
                        onClick = cantFeeding,
                        price = nowFeed.price
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { nowIndex.intValue = max(0, nowIndex.intValue - 1) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Button(
                    onClick = {
                        nowIndex.intValue = min(feedList.value.size - 1, nowIndex.intValue + 1)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                }
            }
        }
        HorizontalPageIndicator(
            pageIndicatorState = pageIndicatorState,
            selectedColor = PaymongNavy,
            unselectedColor = Color.White,
            indicatorSize = 6.dp,
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 7.dp)
        )
    }
}