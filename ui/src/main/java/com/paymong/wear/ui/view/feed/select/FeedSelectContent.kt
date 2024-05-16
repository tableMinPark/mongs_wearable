package com.paymong.wear.ui.view.feed.select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import com.paymong.wear.domain.refac.vo.FoodCodeVo
import com.paymong.wear.domain.refac.vo.SlotVo
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.LoadingBar
import com.paymong.wear.ui.global.component.PayPoint
import com.paymong.wear.ui.global.theme.PaymongNavy
import com.paymong.wear.ui.view.feed.select.component.FeedButton
import com.paymong.wear.ui.view.feed.select.component.FeedItem
import com.paymong.wear.ui.global.dialog.FeedItemDetail
import kotlin.math.max
import kotlin.math.min

@Composable
fun FeedSelectContent(
    slotVo: State<SlotVo>,
    feedItemList: State<List<FoodCodeVo>>,
    isLoadingBarShow: State<Boolean>,
    isFeedItemListLoad: State<Boolean>,
    feed: (String) -> Unit,
    cantFeed: () -> Unit,
) {
    /** 아이템 상세 화면 **/
    val isFeedItemDetailShow = remember{ mutableStateOf(false) }
    /** 페이지 상태 **/
    val feedItemIndex = remember{ mutableIntStateOf(0) }

    /** 페이지 표시기 **/
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = feedItemIndex.intValue
            override val pageCount: Int
                get() = feedItemList.value.size
        }
    }

    /** 포인트, 상점 아이템 이름, 상점 아이템 이미지, 구매 버튼 **/
    if (isFeedItemListLoad.value) {
        Box(
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = if (isLoadingBarShow.value) 0.4f else 0.2f))
                .zIndex(1f)
                .padding(top = 5.dp, bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f)
                ) {
                    PayPoint(payPoint = slotVo.value.payPoint)
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                ) {
                    if (feedItemList.value.size > feedItemIndex.intValue) {
                        val feedItem = feedItemList.value[feedItemIndex.intValue]
                        FeedItem(
                            name = feedItem.name,
                            code = feedItem.code,
                            isFeedItemDetailShow = { isFeedItemDetailShow.value = true }
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                ) {
                    if (feedItemList.value.size > feedItemIndex.intValue) {
                        val feedItem = feedItemList.value[feedItemIndex.intValue]
                        val disable = slotVo.value.payPoint < feedItem.price || !feedItem.isCanBuy

                        FeedButton(
                            disable = disable,
                            price = feedItem.price,
                            onClick = {
                                if (!disable) {
                                    feed(feedItem.code)
                                } else {
                                    cantFeed()
                                }
                            },
                            modifier = Modifier
                        )
                    }
                }
            }
        }

        /** 좌 우 버튼 **/
        Box(
            modifier = Modifier
                .zIndex(2f)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            feedItemIndex.intValue = max(0, feedItemIndex.intValue - 1)
                        },
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
                            feedItemIndex.intValue =
                                min(feedItemList.value.size - 1, feedItemIndex.intValue + 1)
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
        }

        /** 페이지 표시기 **/
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .zIndex(2f)
                .padding(bottom = 7.dp)
        ) {
            HorizontalPageIndicator(
                pageIndicatorState = pageIndicatorState,
                selectedColor = PaymongNavy,
                unselectedColor = Color.White,
                indicatorSize = 6.dp
            )
        }
    }

    /** 로딩 스피너 표출 (상점 아이템 리스트 로딩 시) **/
    if (isLoadingBarShow.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = 0.4f))
                .fillMaxSize()
                .zIndex(3f)
        ) {
            LoadingBar(size = 50)
        }
    }

    if (isFeedItemDetailShow.value) {
        if (feedItemList.value.size > feedItemIndex.intValue) {
            val feedItem = feedItemList.value[feedItemIndex.intValue]
            FeedItemDetail(
                feedItemDisable = { isFeedItemDetailShow.value = false },
                addWeight = feedItem.addWeight,
                addStrength = feedItem.addStrength,
                addSatiety = feedItem.addSatiety,
                addHealthy = feedItem.addHealthy,
                addSleep = feedItem.addSleep
            )
        }
    }
}