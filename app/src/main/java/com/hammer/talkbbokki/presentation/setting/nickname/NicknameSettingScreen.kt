package com.hammer.talkbbokki.presentation.setting.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.button.ButtonType
import com.hammer.talkbbokki.ui.button.CommonLargeButton
import com.hammer.talkbbokki.ui.theme.Category02
import com.hammer.talkbbokki.ui.theme.Error01
import com.hammer.talkbbokki.ui.theme.Gray05
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.Transparent
import com.hammer.talkbbokki.ui.theme.White

@Composable
fun NicknameSettingScreen(
    modifier: Modifier = Modifier,
    textState: Int,
    checkNickname: (String) -> Unit,
    onClickSend: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var textField by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MainColor02)
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
            .clickable(enabled = false) {}
    ) {
        val (header, field, button) = createRefs()

        NicknameSettingHeader(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onBackClick = onBackClick
        )

        NicknameSettingTextField(
            modifier = Modifier.constrainAs(field) {
                top.linkTo(header.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            verifyMessage = textState,
            enableOutline = textState > 0,
            text = textField.trim(),
            textChange = {
                checkNickname(it.trim())
                textField = it
            }
        )

        NicknameSettingButton(
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isEnable = textState == R.string.setting_nickname_usable
        ) {
            onClickSend(textField)
        }
    }
}

@Composable
fun NicknameSettingHeader(
    modifier: Modifier,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.setting_nickname_title),
            style = TalkbbokkiTypography.b2_bold,
            color = White
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onBackClick() }
        )
    }
}

@Composable
fun NicknameSettingTextField(
    modifier: Modifier,
    verifyMessage: Int,
    enableOutline: Boolean,
    text: String,
    textChange: (String) -> Unit
) {
    val messageColor = when (verifyMessage) {
        R.string.setting_nickname_usable -> Category02
        else -> Error01
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.setting_nickname_info),
            style = TalkbbokkiTypography.b2_regular,
            color = White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp),
                singleLine = true,
                maxLines = 1,
                value = text,
                onValueChange = {
                    textChange(it)
                },
                textStyle = TalkbbokkiTypography.b3_regular,
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.setting_nickname_text_field_hint),
                        style = TalkbbokkiTypography.b3_regular,
                        color = Gray05
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = White,
                    disabledTextColor = Transparent,
                    backgroundColor = Gray06,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    errorIndicatorColor = messageColor,
                    cursorColor = MainColor01
                ),
                isError = enableOutline
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (verifyMessage > 0) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_caption),
                    contentDescription = "",
                    tint = messageColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = verifyMessage),
                    style = TalkbbokkiTypography.caption,
                    color = messageColor
                )
            }
        }
    }
}

@Composable
fun NicknameSettingButton(
    modifier: Modifier,
    isEnable: Boolean,
    onClickSend: () -> Unit
) {
    CommonLargeButton(
        isEnable = isEnable,
        text = stringResource(id = R.string.setting_nickname_set_button),
        type = ButtonType.LargePink,
        modifier = modifier
    ) {
        onClickSend()
    }
}
