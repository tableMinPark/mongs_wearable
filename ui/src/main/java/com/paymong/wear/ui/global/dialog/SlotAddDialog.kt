package com.paymong.wear.ui.global.dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.global.component.BlueButton
import com.paymong.wear.ui.global.component.InputBox


@Composable
fun SlotAdd(
    addSlot: (String) -> Unit,
    isSlotAddDisable: () -> Unit,
    context: Context = LocalContext.current
) {
    val inputName = remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.95f))
            .fillMaxSize()
            .zIndex(3f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                Text(text = "몽 이름", fontSize = 15.sp)
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                InputBox(
                    inputName = inputName.value,
                    changeInputName = { inputName.value = it }
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                BlueButton(
                    disable = false,
                    text = "취소",
                    onClick = isSlotAddDisable,
                    modifier = Modifier.padding(end = 5.dp)
                )
                BlueButton(
                    disable = false,
                    text = "생성",
                    onClick = {
                        if (inputName.value.isNotEmpty()) {
                            addSlot(inputName.value)
                            inputName.value = ""
                            isSlotAddDisable()
                        } else {
                            Toast.makeText(
                                context,
                                "이름 입력 필수",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}