package com.paymong.wear.ui.view.slotSelect.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.paymong.wear.domain.code.ShiftCode
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.Mong
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.ui.view.slotSelect.component.SlotButton

@Composable
fun Slot(
    isSelected: Boolean,
    code: String,
    name: String,
    shiftCode: ShiftCode,
    selectSlot: () -> Unit,
    slotDetailShow: () -> Unit,
    slotGraduationShow: () -> Unit,
    slotDeleteShow: () -> Unit,
    context: Context = LocalContext.current
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
        ) {
            Text(text = name, fontSize = 14.sp, maxLines = 1)
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
        ) {
            if (shiftCode == ShiftCode.DEAD) {
                Image(
                    modifier = Modifier
                        .size((120 * 0.6f).dp),
                    painter = painterResource(R.drawable.rip),
                    contentDescription = null
                )
            } else {
                Mong(
                    mong = MongResourceCode.valueOf(code),
                    onClick = slotDetailShow,
                    ratio = 0.6f,
                    modifier = Modifier
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            if (shiftCode == ShiftCode.GRADUATION_READY) {
                SlotButton(
                    text = "졸업",
                    onClick = slotGraduationShow,
                    modifier = Modifier.padding(end = 2.dp)
                )
            } else {
                SlotButton(
                    text = "삭제",
                    onClick = slotDeleteShow,
                    modifier = Modifier.padding(end = 2.dp)
                )
            }

            if (shiftCode != ShiftCode.DELETE && shiftCode != ShiftCode.DEAD) {
                SlotButton(
                    disable = isSelected,
                    text = "선택",
                    onClick = {
                        if (!isSelected) {
                            selectSlot()
                        } else {
                            Toast.makeText(
                                context,
                                "이미 선택된 몽",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }

    }
}
