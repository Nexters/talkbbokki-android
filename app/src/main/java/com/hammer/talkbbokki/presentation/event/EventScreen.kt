package com.hammer.talkbbokki.presentation.event

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.analytics.AnalyticsConst
import com.hammer.talkbbokki.analytics.logEvent
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.detail.CardFace
import com.hammer.talkbbokki.presentation.detail.DetailBottomNavigation
import com.hammer.talkbbokki.presentation.detail.DetailFlipCard
import com.hammer.talkbbokki.presentation.detail.DetailHeader
import com.hammer.talkbbokki.presentation.showPageAd
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White
import com.hammer.talkbbokki.ui.util.toHexColor
import kotlinx.coroutines.delay

@Composable
fun EventListRoute(
    onClickBack: () -> Unit,
    onClickToComments: (Int, Int) -> Unit,
    viewModel: EventViewModel = hiltViewModel()
) {
    val item by viewModel.currentTopic.collectAsState()
    val hasPrevAndNext by viewModel.hasPrevAndNext.collectAsState()
    val commentsCount by viewModel.commentsCount.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    var showToast by remember(toastMessage) { mutableStateOf(toastMessage > 0) }
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .size(170.dp, 180.dp)
                    .padding(top = 30.dp)
                    .clickable { showDialog = false }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_smile_face),
                    contentDescription = null,
                    alignment = Alignment.Center
                )
                Text(
                    text = stringResource(R.string.detail_image_share_complete),
                    modifier = Modifier.align(Alignment.BottomCenter),
                    style = TalkbbokkiTypography.b1_bold,
                    color = White
                )
            }
        }
        LaunchedEffect(showDialog) {
            delay(1500)
            showDialog = false
        }
    }

    val context = LocalContext.current

    EventScreen(
        item = item,
        isBookmarked = isBookmarked,
        hasPrevAndNext = hasPrevAndNext,
        commentsCount = commentsCount,
        updateViewCnt = { viewModel.postViewCnt(item.id) },
        onClickShowDialog = { showDialog = true },
        onClickComment = { onClickToComments(item.id, commentsCount) },
        onClickBookmark = {
            if (it) viewModel.addBookmark() else viewModel.removeBookmark()
            logEvent(
                AnalyticsConst.Event.CLICK_CARD_BOOKMARK,
                hashMapOf(
                    AnalyticsConst.Key.TOPIC_ID to item.id.toString(),
                    AnalyticsConst.Key.TOGGLE to it.toString()
                )
            )
        },
        onClickPrev = {
            viewModel.clickPrev()
        },
        onClickNext = {
            showPageAd(context) {
                viewModel.clickNext()
            }
        },
        onClickBack = onClickBack
    )

    if (showToast) {
        Toast.makeText(context, stringResource(id = toastMessage), Toast.LENGTH_SHORT)
            .show()
        showToast = false
    }
}

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    item: TopicItem,
    hasPrevAndNext: Pair<Boolean, Boolean>,
    commentsCount: Int,
    isBookmarked: Boolean,
    updateViewCnt: () -> Unit,
    onClickShowDialog: () -> Unit,
    onClickComment: () -> Unit,
    onClickBookmark: (Boolean) -> Unit,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit,
    onClickBack: () -> Unit
) {
    var cardFace by remember { mutableStateOf(CardFace.FRONT) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(item.bgColor.toHexColor())
    ) {
        DetailHeader(cardFace = cardFace, onBackClick = { cardFace = CardFace.BACK })
        Box(modifier = modifier.fillMaxSize()) {
            DetailFlipCard(
                modifier = Modifier.align(Alignment.Center),
                cardFace = cardFace,
                item = item,
                isBookmarked = isBookmarked,
                onClickToList = onClickBack,
                updateViewCnt = updateViewCnt,
                onClickShowDialog = onClickShowDialog,
                onClickBookmark = onClickBookmark
            )
        }
        DetailBottomNavigation(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            hasPrev = hasPrevAndNext.first,
            hasNext = hasPrevAndNext.second,
            commentsCount = commentsCount,
            onClickComment = { onClickComment() },
            onClickPrev = { onClickPrev() },
            onClickNext = { onClickNext() }
        )
    }
}
