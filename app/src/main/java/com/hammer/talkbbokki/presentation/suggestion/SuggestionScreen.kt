package com.hammer.talkbbokki.presentation.suggestion

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.button.ButtonType
import com.hammer.talkbbokki.ui.button.CommonLargeButton
import com.hammer.talkbbokki.ui.theme.Gray04
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White

@Composable
fun SuggestionRoute(
    onBackClick: () -> Unit,
    viewModel: SuggestionViewModel = hiltViewModel()
) {
    val showSuccess by viewModel.suggestSuccess.collectAsState()
    if (!showSuccess) {
        SuggestionScreen(
            onBackClick = onBackClick,
            onClickSend = { viewModel.sendSuggestion(it) }
        )
    } else {
        SuggestionSuccess { onBackClick() }
    }
}

@Composable
fun SuggestionSuccess(
    onClickGoHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor02)
            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.image_smile_face),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.suggestion_success_message),
                style = TalkbbokkiTypography.b1_bold,
                color = White
            )
        }
        CommonLargeButton(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            text = stringResource(id = R.string.suggestion_go_home_button),
            type = ButtonType.LargePink
        ) { onClickGoHome() }
    }
}

@Composable
fun SuggestionScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onClickSend: (String) -> Unit
) {
    val textLimitCount = 200
    var textField by remember { mutableStateOf("") }
    if (textField.count() >= textLimitCount) {
        Toast.makeText(
            LocalContext.current,
            stringResource(id = R.string.suggestion_text_limit),
            Toast.LENGTH_SHORT
        ).show()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainColor02)
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SuggestionHeader(
                modifier = modifier,
                onBackClick = onBackClick
            )
            SuggestionTextField(
                text = textField,
                textChange = {
                    if (it.count() <= textLimitCount) textField = it
                }
            )
        }

        SuggestionSendButton(modifier.align(Alignment.BottomCenter)) {
            if (textField.isNotEmpty()) onClickSend(textField)
        }
    }
}

@Composable
fun SuggestionHeader(
    modifier: Modifier,
    onBackClick: () -> Unit
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = White,
                modifier = Modifier.clickable { onBackClick() }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.suggestion_title),
            style = TalkbbokkiTypography.h2_bold,
            color = White
        )
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun SuggestionTextField(
    text: String,
    textChange: (String) -> Unit
) {
    var showHint by remember { mutableStateOf(true) }

    Box {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 186.dp),
            value = text,
            onValueChange = {
                showHint = it.isEmpty()
                textChange(it)
            },
            textStyle = TalkbbokkiTypography.b2_regular,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = White,
                disabledTextColor = Color.Transparent,
                backgroundColor = Gray06,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MainColor01
            )
        )
        if (showHint) {
            Text(
                text = stringResource(id = R.string.suggestion_text_field_hint),
                style = TalkbbokkiTypography.b2_regular,
                color = Gray04,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun SuggestionSendButton(
    modifier: Modifier,
    onClickSend: () -> Unit
) {
    CommonLargeButton(
        text = stringResource(id = R.string.suggestion_send_button),
        type = ButtonType.LargePink,
        modifier = modifier
    ) {
        onClickSend()
    }
}
