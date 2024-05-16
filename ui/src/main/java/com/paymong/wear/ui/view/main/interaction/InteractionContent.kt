package com.paymong.wear.ui.view.main.interaction

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paymong.wear.ui.R
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.domain.vo.SlotVo
import com.paymong.wear.ui.view.main.interaction.component.Interaction


@Composable
fun MainInteractionContent(
    slotVo: State<SlotVo>,
    training: () -> Unit,
    battle: () -> Unit,
    feed: () -> Unit,
    sleep: () -> Unit,
    poop: () -> Unit,
    collection: () -> Unit,
    slotSelect: () -> Unit,
    context: Context = LocalContext.current
) {
    val isEgg = slotVo.value.mongCode in listOf("CH000", "CH001", "CH002", "CH003", "CH004", "CH005")
    val isMongEmpty = slotVo.value.shiftCode == ShiftCode.EMPTY || slotVo.value.shiftCode == ShiftCode.DELETE || slotVo.value.shiftCode == ShiftCode.DEAD
    val isGraduateReady = slotVo.value.shiftCode == ShiftCode.GRADUATION_READY

    Box(
        modifier = Modifier
            .padding(top = 13.dp, start = 4.dp, end = 4.dp, bottom = 13.dp)
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Interaction(
                    icon = R.drawable.feed,
                    border = R.drawable.interaction_bnt_yellow,
                    onClick = {
                          if (isMongEmpty || isGraduateReady) {
                              Toast.makeText(
                                  context,
                                  "슬롯 선택 필요",
                                  Toast.LENGTH_SHORT
                              ).show()
                          } else if (isEgg) {
                              Toast.makeText(
                                  context,
                                  "알 상태에서는 불가능",
                                  Toast.LENGTH_SHORT
                              ).show()
                          } else {
                              feed()
                          }
                    },
                    modifier = Modifier.padding(end = 4.dp)
                )
                Interaction(
                    icon = R.drawable.collection,
                    border = R.drawable.interaction_bnt_orange,
                    onClick = collection,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Interaction(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    onClick = {
                        if (isMongEmpty || isGraduateReady) {
                            Toast.makeText(
                                context,
                                "슬롯 선택 필요",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (isEgg) {
                            Toast.makeText(
                                context,
                                "알 상태에서는 불가능",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            sleep()
                        }
                    },
                    modifier = Modifier.padding(end = 4.dp)
                )
                Interaction(
                    icon = R.drawable.slot,
                    border = R.drawable.interaction_bnt_red,
                    onClick = slotSelect,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .padding(end = 4.dp)
                )
                Interaction(
                    icon = R.drawable.poop,
                    border = R.drawable.interaction_bnt_purple,
                    onClick = {
                        if (isMongEmpty || isGraduateReady) {
                            Toast.makeText(
                                context,
                                "슬롯 선택 필요",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (isEgg) {
                            Toast.makeText(
                                context,
                                "알 상태에서는 불가능",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            poop()
                        }
                    },
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Interaction(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = {
                        if (isMongEmpty) {
                            Toast.makeText(
                                context,
                                "슬롯 선택 필요",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (isEgg) {
                            Toast.makeText(
                                context,
                                "알 상태에서는 불가능",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            training()
                        }
                    },
                    modifier = Modifier.padding(end = 4.dp)
                )
                Interaction(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = {
                        if (isMongEmpty) {
                            Toast.makeText(
                                context,
                                "슬롯 선택 필요",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (isEgg) {
                            Toast.makeText(
                                context,
                                "알 상태에서는 불가능",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            battle()
                        }
                    },
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
