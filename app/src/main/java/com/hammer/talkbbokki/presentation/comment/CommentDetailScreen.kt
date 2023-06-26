package com.hammer.talkbbokki.presentation.comment

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.dialog.CommonDialog
import com.hammer.talkbbokki.ui.theme.*

@Composable
fun CommentDetailRoute(
    onBackClick: () -> Unit,
    onReportClick: (CommentModel) -> Unit,
    viewModel: ChildCommentsViewModel = hiltViewModel(),
) {
    val parentComment = viewModel.parentComment
    val recomments by viewModel.commentItems.collectAsState()

    var tempDeleteComment by remember { mutableStateOf<CommentModel?>(null) }
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()
    if (showDeleteDialog) {
        CommonDialog(
            showIcon = false,
            text = stringResource(R.string.comment_delete_dialog_text),
            subText = null,
            agreeText = stringResource(R.string.comment_delete_dialog_ok),
            agreeAction = {
                tempDeleteComment?.let {
                    viewModel.deleteComment(it)
                }
            },
            disagreeText = stringResource(R.string.comment_delete_dialog_cancel),
            disagreeAction = {
                viewModel.closeDeleteDialog()
            },
        )
    }

    if (parentComment == null) {
        onBackClick()
    } else {
        CommentDetailScreen(
            onBackClick,
            parentComment,
            recomments,
            onReportClick = { onReportClick(it) },
            onClickPostComment = { viewModel.postComment(it) },
            onDeleteClick = {
                tempDeleteComment = it
                viewModel.showDeleteDialog()
            },
        )
    }
}

@Composable
fun CommentDetailScreen(
    onBackClick: () -> Unit,
    comment: CommentModel,
    recomments: List<CommentModel>,
    onClickPostComment: (String) -> Unit,
    onReportClick: (CommentModel) -> Unit,
    onDeleteClick: (CommentModel) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MainBackgroundColor),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 78.dp),
        ) {
            CommentDetailHeader(onBackClick)
            CommentContents(
                isParentComment = true,
                comment = comment,
                onReportClick = { onReportClick(it) },
                onDeleteClick = { onDeleteClick(it) },
            )
            RecommentList(
                recomments,
                onReportClick = { onReportClick(it) },
                onDeleteClick = { onDeleteClick(it) },
            )
        }
        Box(Modifier.align(Alignment.BottomCenter)) {
            CommentInputArea { onClickPostComment(it) }
        }
    }
}

@Composable
fun CommentDetailHeader(
    onBackClick: () -> Unit,
) {
    val replyCount = 0
    Box(
        Modifier
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            tint = White,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
                .align(Alignment.CenterStart)
                .padding(start = 20.dp),
        )
        Text(
            text = "답글 ($replyCount)",
            Modifier.align(Alignment.Center),
            color = White,
        )
    }
}

@Composable
fun RecommentItem(
    comment: CommentModel,
    onReportClick: (CommentModel) -> Unit,
    onDeleteClick: (CommentModel) -> Unit,
) {
    Box(modifier = Modifier.padding(20.dp)) {
        Image(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = R.drawable.ic_recomment),
            contentDescription = null,
        )
        CommentContents(
            isParentComment = false,
            comment = comment,
            onReportClick = { onReportClick(it) },
            onDeleteClick = onDeleteClick,
        )
    }
}

@Composable
fun CommentContents(
    isParentComment: Boolean,
    comment: CommentModel,
    onReportClick: (CommentModel) -> Unit,
    onDeleteClick: (CommentModel) -> Unit,
) {
    val isMine = comment.isMine
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 26.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(9f),
            ) {
                Text(
                    text = comment.nickname,
                    style = TalkbbokkiTypography.b3_bold,
                    color = if (isMine) MainColor01 else White,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = comment.date, style = TalkbbokkiTypography.caption, color = Gray06)
            }

            if (!isParentComment && isMine) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    tint = White,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onDeleteClick(comment)
                        }
                        .weight(0.6f),
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = comment.content, style = TalkbbokkiTypography.b3_regular, color = White)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isParentComment) {
                Text(
                    text = stringResource(R.string.recommnet_count, comment.replyCount),
                    style = TalkbbokkiTypography.caption,
                    color = Gray06,
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            if (!isMine) {
                Text(
                    text = stringResource(R.string.report_comment_button),
                    modifier = Modifier.clickable {
                        onReportClick(comment)
                    },
                    style = TalkbbokkiTypography.caption,
                    color = Gray06,
                )
            }
        }
    }
}

@Composable
fun RecommentList(
    comments: List<CommentModel>,
    onReportClick: (CommentModel) -> Unit,
    onDeleteClick: (CommentModel) -> Unit,
) {
    LazyColumn {
        itemsIndexed(comments) { idx, comment ->
            RecommentItem(
                comment = comment,
                onReportClick = { onReportClick(comment) },
                onDeleteClick = { onDeleteClick(comment) },
            )
        }
    }
}

@Composable
fun RecommentInputArea() {
    val commentText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.run {
            fillMaxWidth()
                .defaultMinSize(minHeight = 78.dp)
                .background(MainBackgroundColor)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray06, RoundedCornerShape(8.dp)),
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
                    cursorColor = MainColor01,
                ),
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
