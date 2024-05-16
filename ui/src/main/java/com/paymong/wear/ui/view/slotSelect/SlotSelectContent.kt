package com.paymong.wear.ui.view.slotSelect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.dialog.ConfirmDialog
import com.paymong.wear.ui.global.theme.PaymongNavy
import com.paymong.wear.ui.view.slotSelect.component.SlotBuy
import com.paymong.wear.ui.view.slotSelect.component.Slot
import com.paymong.wear.ui.view.slotSelect.component.SlotEmpty
import com.paymong.wear.ui.global.dialog.SlotAdd
import com.paymong.wear.ui.global.dialog.SlotDetailDialog
import kotlin.math.max
import kotlin.math.min

const val MAX_SLOT_SIZE = 1

@Composable
fun SlotSelectContent(
    slotBuyPrice: State<Int>,
    starPoint: State<Int>,
    maxSlot: State<Int>,
    slotList: State<List<SlotVo>>,
    isLoadingBarShow: State<Boolean>,
    isSlotListLoad: State<Boolean>,
    addSlot: (String) -> Unit,
    buySlot: () -> Unit,
    selectSlot: (Long) -> Unit,
    removeSlot: (Long) -> Unit,
    graduationSlot: (Long) -> Unit,
) {
    val isSlotDetailShow = remember{ mutableStateOf(false) }
    val isSlotDeleteShow = remember{ mutableStateOf(false) }
    val isSlotAddShow = remember { mutableStateOf(false) }
    val isSlotGraduationShow = remember{ mutableStateOf(false) }
    /** 페이지 상태 **/
    val slotIndex = remember{ mutableIntStateOf(0) }
    val maxSlotSize = max(MAX_SLOT_SIZE, slotList.value.size)       // 최대 슬롯 사이즈를 정하는데, 리스트 크기를 우선시 함

    /** 페이지 표시기 **/
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = 0f
            override val selectedPage: Int
                get() = slotIndex.intValue
            override val pageCount: Int
                get() = maxSlotSize
        }
    }

    if (isSlotListLoad.value) {
        Box(
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = 0.2f))
                .zIndex(1f)
                .padding(top = 10.dp, bottom = 20.dp)
        ) {
            if (slotIndex.intValue < slotList.value.size) {
                val slot = slotList.value[slotIndex.intValue]
                Slot(
                    isSelected = slot.isSelected,
                    code = slot.mongCode,
                    name = slot.name,
                    shiftCode = slot.shiftCode,
                    selectSlot = { selectSlot(slot.mongId) },
                    slotDetailShow = { isSlotDetailShow.value = true },
                    slotDeleteShow = { isSlotDeleteShow.value = true },
                    slotGraduationShow = { isSlotGraduationShow.value = true },
                )
            } else if (maxSlot.value > slotIndex.intValue) {
                SlotEmpty(
                    slotAddShow = { isSlotAddShow.value = true },
                )
            } else {
                SlotBuy(
                    starPoint = starPoint.value,
                    slotBuyPrice = slotBuyPrice.value,
                    buySlot = buySlot
                )
            }
        }

        /** 좌 우 버튼 **/
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .zIndex(2f)
                .padding(bottom = 7.dp)
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
                            slotIndex.intValue = max(0, slotIndex.intValue - 1)
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
                            slotIndex.intValue = min(maxSlotSize - 1, slotIndex.intValue + 1)
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

    if (isSlotAddShow.value) {
        SlotAdd(
            isSlotAddDisable = { isSlotAddShow.value = false },
            addSlot = { name -> addSlot(name) }
        )
    }

    if (isSlotDetailShow.value) {
        if (slotList.value.size > slotIndex.intValue) {
            val slot = slotList.value[slotIndex.intValue]
            SlotDetailDialog(
                isSlotDetailDisable = { isSlotDetailShow.value = false },
                mongId = slot.mongId,
                name = slot.name,
                weight = slot.weight,
                healthy = slot.healthy,
                satiety = slot.satiety,
                strength = slot.strength,
                sleep = slot.sleep,
                payPoint = slot.payPoint,
                born = slot.born,
            )
        }
    }

    if (isSlotDeleteShow.value) {
        if (slotList.value.size > slotIndex.intValue) {
            val slot = slotList.value[slotIndex.intValue]
            ConfirmDialog(
                text = "삭제 하시겠습니까?",
                confirm = {
                    removeSlot(slot.mongId)
                    isSlotDeleteShow.value = false
                },
                cancel = { isSlotDeleteShow.value = false }
            )
        }
    }

    if (isSlotGraduationShow.value) {
        if (slotList.value.size > slotIndex.intValue) {
            val slot = slotList.value[slotIndex.intValue]
            ConfirmDialog(
                text = "졸업 시키시겠습니까?\n슬롯에서 삭제됩니다.",
                confirm = {
                    graduationSlot(slot.mongId)
                    isSlotGraduationShow.value = false
                },
                cancel = { isSlotGraduationShow.value = false }
            )
        }
    }
}
