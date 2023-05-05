package com.hammer.talkbbokki.presentation.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.*

@Composable
fun CommentsRoute(
    onBackClick: () -> Unit, viewModel: CommentsViewModel = hiltViewModel()
) {
    val comments = viewModel.getComments()
    CommentsScreen(
        onBackClick,
        comments
    )
}

@Composable
fun CommentsScreen(
    onBackClick: () -> Unit,
    comments: List<Comment>
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MainBackgroundColor)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 78.dp)
        ) {
            CommentsHeader(onBackClick)
            CommentList(
                comments
            )
        }
        Box(Modifier.align(Alignment.BottomCenter)) {
            CommentInputArea()
        }
    }
}

@Composable
fun CommentsHeader(
    onBackClick: () -> Unit,
) {
    val replyCount = 0
    Box(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_arrow_left),
            tint = White,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
                .align(Alignment.CenterStart)
                .padding(start = 20.dp))
        Text(
            text = "댓글 ($replyCount)", Modifier.align(Alignment.Center), color = White
        )
    }
}

@Composable
fun CommentItem(
    nickname: String, date: String, content: String, replyCount: Int, onDeleteClick: () -> Unit
) {
    val isMine = false
    Column(modifier = Modifier.padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = nickname, style = TalkbbokkiTypography.b3_bold,
                color = if (isMine) MainColor01 else White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = date, style = TalkbbokkiTypography.caption, color = Gray06)
        }
        if (isMine) {
            Icon(painter = painterResource(id = R.drawable.ic_close),
                tint = White,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        // 삭제하기
                    }
                    .align(Alignment.End))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = content, style = TalkbbokkiTypography.b3_regular, color = White)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (replyCount == 0) stringResource(R.string.recomment_button) else "답글($replyCount)",
                style = TalkbbokkiTypography.caption,
                color = Gray06,
                modifier = Modifier.clickable { onDeleteClick() },
            )
            Spacer(modifier = Modifier.width(12.dp))
            if (!isMine) {
                Text(
                    text = stringResource(R.string.report_comment_button),
                    modifier = Modifier.clickable { onDeleteClick() },
                    style = TalkbbokkiTypography.caption,
                    color = Gray06
                )
            }
        }
    }
}

@Composable
fun CommentList(comments: List<Comment>) {
    LazyColumn {
        itemsIndexed(comments) { idx, comment ->
            CommentItem(
                nickname = comment.nickname,
                date = comment.date,
                content = comment.content,
                replyCount = comment.replyCount,
                onDeleteClick = comment.onDeleteClick
            )
        }
    }
}

@Composable
fun CommentInputArea() {
    val commentText = remember { mutableStateOf("") }
    Column(modifier = Modifier.run {
        fillMaxWidth()
            .defaultMinSize(minHeight = 78.dp)
            .background(MainBackgroundColor)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray06, RoundedCornerShape(8.dp))
        ) {
            TextField(
                modifier = Modifier
                    .weight(5f)
                    .defaultMinSize(minHeight = 46.dp),
                value = commentText.value,
                onValueChange = {
                    commentText.value = it
                },
                textStyle = TalkbbokkiTypography.b2_regular,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = White,
                    disabledTextColor = Transparent,
                    backgroundColor = Transparent,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    cursorColor = MainColor01
                )
            )
            Box(
                modifier = Modifier
                    .clickable {
                        // 댓글 작성
                    }
                    .align(Alignment.Bottom)
                    .size(height = 48.dp, width = 52.dp)
                    .padding(horizontal = 12.dp),
            ) {
                Text(
                    stringResource(R.string.comment_send_button),
                    style = TalkbbokkiTypography.b2_bold,
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    color = White,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

data class Comment(
    val nickname: String,
    val date: String,
    val content: String,
    val replyCount: Int,
    val onDeleteClick: () -> Unit
)