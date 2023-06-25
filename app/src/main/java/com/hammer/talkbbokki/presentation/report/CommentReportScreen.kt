package com.hammer.talkbbokki.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.button.ButtonType
import com.hammer.talkbbokki.ui.button.CommonLargeButton
import com.hammer.talkbbokki.ui.dialog.CommonDialog
import com.hammer.talkbbokki.ui.theme.Gray05
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White

@Composable
fun CommentReportScreen(
    modifier: Modifier = Modifier,
    onClickClose: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val writer by viewModel.writer.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val reasonList by viewModel.reportReasons.collectAsState()
    val sendButtonEnable by viewModel.sendButtonEnable.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
        CommonDialog(
            showIcon = false,
            text = stringResource(id = R.string.comment_report_send_success),
            subText = null,
            agreeText = stringResource(id = R.string.dialog_ok),
            agreeAction = {
                onClickClose()
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainColor02)
    ) {
        Column {
            ReportTopNavigation {
                onClickClose()
            }
            ReportHeader(
                writer = writer,
                comments = comments
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Gray06,
                thickness = 12.dp
            )
            ReportReasons(
                reasonList = reasonList
            ) { index ->
                viewModel.onChangedReason(index)
            }
        }
        SendReportButton(
            modifier = modifier
                .align(Alignment.BottomCenter),
            isEnable = sendButtonEnable
        ) {
            viewModel.sendReport()
        }
    }
}

@Composable
fun ReportTopNavigation(
    modifier: Modifier = Modifier,
    onClickClose: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.CenterStart)
                .clickable { onClickClose() }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.comment_report_title),
            style = TalkbbokkiTypography.b2_bold,
            color = White
        )
    }
}

@Composable
fun ReportHeader(
    modifier: Modifier = Modifier,
    writer: String,
    comments: String
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
    ) {
        val (writerFieldRef, writerRef, commentsFieldRef, commentRef) = createRefs()

        Text(
            modifier = Modifier.constrainAs(writerFieldRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            text = stringResource(id = R.string.comment_report_writer),
            style = TalkbbokkiTypography.b2_regular,
            color = Gray05
        )

        Text(
            modifier = Modifier
                .constrainAs(writerRef) {
                    baseline.linkTo(writerFieldRef.baseline)
                    start.linkTo(writerFieldRef.end)
                }
                .padding(start = 12.dp)
                .width(290.dp),
            text = writer,
            style = TalkbbokkiTypography.b2_regular,
            color = White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .constrainAs(commentsFieldRef) {
                    top.linkTo(writerFieldRef.bottom)
                    start.linkTo(writerFieldRef.start)
                }
                .padding(top = 24.dp),
            text = stringResource(id = R.string.comment_report_comment_contents),
            style = TalkbbokkiTypography.b2_regular,
            color = Gray05
        )

        Text(
            modifier = Modifier
                .constrainAs(commentRef) {
                    baseline.linkTo(commentsFieldRef.baseline)
                    start.linkTo(writerRef.start)
                }
                .padding(start = 12.dp)
                .width(290.dp),
            text = comments,
            style = TalkbbokkiTypography.b2_regular,
            color = White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun ReportReasons(
    modifier: Modifier = Modifier,
    reasonList: List<ReportReasonItem>,
    onSelectedReason: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        this.itemsIndexed(reasonList) { index, item ->
            ReportReason(
                index = index,
                reason = item
            ) { index ->
                onSelectedReason(index)
            }
        }
    }
}

@Composable
fun ReportReason(
    modifier: Modifier = Modifier,
    index: Int,
    reason: ReportReasonItem,
    onCheckChange: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (checkBoxRef, textRef) = createRefs()
        Checkbox(
            modifier = Modifier.constrainAs(checkBoxRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            checked = reason.checked,
            onCheckedChange = {
                onCheckChange(index)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MainColor01,
                uncheckedColor = Gray06
            )
        )
        Text(
            modifier = Modifier.constrainAs(textRef) {
                start.linkTo(checkBoxRef.end)
                top.linkTo(checkBoxRef.top)
                bottom.linkTo(checkBoxRef.bottom)
            }.clickable {
                onCheckChange(index)
            },
            text = reason.reason.keyword,
            style = TalkbbokkiTypography.b2_regular,
            color = White
        )
    }
}

@Composable
fun SendReportButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean,
    onClickSend: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 16.dp, end = 20.dp)
    ) {
        CommonLargeButton(
            isEnable = isEnable,
            type = ButtonType.LargePink,
            text = stringResource(id = R.string.comment_report_send)
        ) {
            onClickSend()
        }
    }
}
