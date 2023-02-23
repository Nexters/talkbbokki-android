package com.hammer.talkbbokki.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White

enum class ButtonType(
    val backgroundColor: Color,
    val textColor: Color
) {
    LargeBlack(
        MainColor02,
        White
    ),
    LargePink(
        MainColor01,
        White
    ),
    LargeWhite(
        White,
        MainColor01
    )
}

@Composable
fun CommonLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.LargeBlack,
    onClickButton: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = type.backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable { onClickButton() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TalkbbokkiTypography.button_large,
            color = type.textColor,
            textAlign = TextAlign.Center
        )
    }
}
