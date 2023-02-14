package com.hammer.talkbbokki.ui.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.Black
import com.hammer.talkbbokki.ui.theme.Gray04
import com.hammer.talkbbokki.ui.theme.Gray05
import com.hammer.talkbbokki.ui.theme.Gray07
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White

@Composable
fun BookmarkCancelDialog(
    @StringRes textRes: Int,
    @StringRes subTextRes: Int,
    @StringRes agreeTextRes: Int,
    agreeAction: () -> Unit,
    @StringRes disagreeTextRes: Int,
    disagreeAction: () -> Unit
) = CommonDialog(
    stringResource(id = textRes),
    stringResource(id = subTextRes),
    stringResource(id = agreeTextRes),
    agreeAction,
    stringResource(id = disagreeTextRes),
    disagreeAction,
    true
)

@Composable
fun CommonDialog(
    text: String,
    subText: String,
    agreeText: String,
    agreeAction: () -> Unit,
    disagreeText: String? = null,
    disagreeAction: (() -> Unit)? = null,
    showIcon: Boolean = true
) {
    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            color = White
        ) {
            DialogContent(
                text,
                subText,
                agreeText,
                agreeAction,
                disagreeText,
                disagreeAction,
                showIcon
            )
        }
    }
}

@Composable
fun DialogContent(
    text: String,
    subText: String,
    agreeText: String,
    agreeAction: () -> Unit,
    disagreeText: String? = null,
    disagreeAction: (() -> Unit)? = null,
    showIcon: Boolean = true
) {
    val buttonBackgroundColor = if (disagreeText != null && disagreeAction != null) {
        Gray05
    } else {
        Gray07
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        if (showIcon) {
            Image(
                painter = painterResource(id = R.drawable.image_cancel_face),
                contentDescription = null
            )
        }
        Text(
            text = text,
            style = TalkbbokkiTypography.b1_bold,
            color = Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subText,
            style = TalkbbokkiTypography.caption,
            color = Gray04,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            if (disagreeText != null && disagreeAction != null) {
                Text(
                    modifier = Modifier
                        .background(
                            color = MainColor01,
                            shape = RoundedCornerShape(8.dp)
                        ).padding(15.dp)
                        .clickable { disagreeAction() },
                    text = disagreeText,
                    style = TalkbbokkiTypography.b2_bold,
                    color = White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                modifier = Modifier
                    .background(
                        color = buttonBackgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    ).padding(15.dp)
                    .clickable { agreeAction() },
                text = agreeText,
                style = TalkbbokkiTypography.b2_bold,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}
