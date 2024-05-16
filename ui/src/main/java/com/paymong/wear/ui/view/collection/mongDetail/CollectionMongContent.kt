package com.paymong.wear.ui.view.collection.mongDetail

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
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.domain.refac.vo.MongCodeVo
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.Mong
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.ui.view.collection.component.ListButton


@Composable
fun CollectionMongContent(
    mongCodeVo: State<MongCodeVo>,
    navCollectionSelect: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .zIndex(1f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.22f)
            ) {
                Text(text = mongCodeVo.value.name)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.53f)
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                ) {
                    Mong(
                        mong = MongResourceCode.valueOf(mongCodeVo.value.code),
                        modifier = Modifier
                            .zIndex(3.2f)
                            .padding(bottom = 10.dp),
                        ratio = 0.7f,
                    )
                    Image(
                        painter = painterResource(R.drawable.mong_shadow),
                        contentDescription = null,
                        modifier = Modifier
                            .size(height = 25.dp, width = 150.dp)
                            .zIndex(3.1f)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
            ) {
                ListButton(
                    onClick = navCollectionSelect
                )
            }
        }
    }
}
