package com.hammer.talkbbokki.presentation.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.analytics.AnalyticsConst
import com.hammer.talkbbokki.analytics.logEvent
import com.hammer.talkbbokki.data.entity.TalkOrderItem
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.shareLink
import com.hammer.talkbbokki.presentation.shareScreenShot
import com.hammer.talkbbokki.ui.theme.*
import com.hammer.talkbbokki.ui.util.toHexColor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailRoute(
    onClickToList: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val toastMessage by viewModel.toastMessage.collectAsState()
    val item by viewModel.currentTopic.collectAsState()
    val starter by viewModel.talkOrder.collectAsState()

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

    DetailScreen(
        onClickToList = onClickToList,
        item = item,
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
        onClickStarter = { viewModel.getTalkStarter() },
        updateViewCnt = { viewModel.postViewCnt(item.id) },
        onClickShowDialog = { showDialog = true },
        starter = starter
    )
    if (showToast) {
        Toast.makeText(LocalContext.current, stringResource(id = toastMessage), Toast.LENGTH_SHORT)
            .show()
        showToast = false
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onClickToList: () -> Unit,
    item: TopicItem,
    onClickBookmark: (Boolean) -> Unit,
    onClickStarter: () -> Unit,
    updateViewCnt: () -> Unit,
    onClickShowDialog: () -> Unit,
    starter: TalkOrderItem
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
                Modifier.align(Alignment.Center),
                cardFace,
                item,
                item.isBookmark,
                starter,
                onClickToList,
                onClickBookmark,
                onClickStarter,
                updateViewCnt,
                onClickShowDialog
            )
        }
    }
}

@Composable
fun DetailHeader(cardFace: CardFace, onBackClick: () -> Unit) {
    if (cardFace == CardFace.FRONT) {
        Icon(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.horizontal_padding),
                    vertical = dimensionResource(id = R.dimen.vertical_padding)
                )
                .size(24.dp)
                .clickable { onBackClick() },
            painter = painterResource(id = R.drawable.ic_arrow_left),
            tint = Black,
            contentDescription = null
        )
    }
}

@Composable
fun DetailBottomNavigation(
    modifier: Modifier = Modifier,
    hasPrev: Boolean,
    hasNext: Boolean,
    commentsCount: Int,
    onClickComment: () -> Unit,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Gray07)
            .shadow(elevation = 10.dp)
            .padding(start = 20.dp, top = 16.dp, bottom = 16.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /* 댓글 정보 */
        Row(
            modifier = Modifier.clickable { onClickComment() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_comments),
                tint = White,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = commentsCount.toString(),
                style = TalkbbokkiTypography.b2_regular,
                color = White
            )
        }

        val prevColor = White.copy(alpha = if (hasPrev) 1f else 0.35f)
        val nextColor = White.copy(alpha = if (hasNext) 1f else 0.35f)
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /* < 이전 카드 */
            Row(
                modifier = Modifier.clickable(enabled = hasPrev) { onClickPrev() }
            ) {
                Icon(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_arrow_prev),
                    tint = prevColor,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.detail_bottom_navigation_prev),
                    style = TalkbbokkiTypography.b2_bold,
                    color = prevColor
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Spacer(
                modifier = Modifier
                    .height(15.dp)
                    .width(1.dp)
                    .background(White.copy(alpha = 0.35f))
            )
            Spacer(modifier = Modifier.width(12.dp))

            /* 다음 카드 > */
            Row(
                modifier = Modifier.clickable(enabled = hasNext) { onClickNext() }
            ) {
                Text(
                    text = stringResource(id = R.string.detail_bottom_navigation_next),
                    style = TalkbbokkiTypography.b2_bold,
                    color = nextColor
                )
                Icon(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_arrow_next),
                    tint = nextColor,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun DetailFlipCard(
    modifier: Modifier = Modifier,
    cardFace: CardFace,
    item: TopicItem,
    isBookmarked: Boolean,
    starter: TalkOrderItem? = null,
    onClickToList: () -> Unit,
    onClickBookmark: ((Boolean) -> Unit)? = null,
    onClickStarter: (() -> Unit)? = null,
    updateViewCnt: () -> Unit,
    onClickShowDialog: () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(1f) }

    FlipCard(
        rotation = rotation,
        modifier = modifier
            .width(dimensionResource(id = R.dimen.scale_up_card_width))
            .aspectRatio(0.71f)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                cameraDistance = 12f
            )
            .background(item.bgColor.toHexColor()),
        front = {
            FrontCardFace(item)
        },
        back = {
            BackCardFace(
                item,
                isBookmarked,
                starter,
                onClickBookmark,
                updateViewCnt,
                onClickStarter,
                onClickShowDialog
            )
        }
    )

    if (cardFace == CardFace.FRONT) {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 0.7f,
                        targetValue = 1.0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }

                    animate(
                        initialValue = 0f,
                        targetValue = 180f,
                        animationSpec = tween(
                            durationMillis = 540,
                            easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }
                }
            }
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 180f,
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 540,
                            easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }

                    animate(
                        initialValue = 1f,
                        targetValue = 0.7f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }
                    onClickToList()
                }
            }
        }
    }
}

@Composable
fun FrontCardFace(item: TopicItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(item.bgColor.toHexColor()),
        contentAlignment = Alignment.Center
    ) {
        val cardImage = painterResource(id = R.drawable.bg_card_large)
        val tag = when (item.tag) {
            "LOVE" -> {
                painterResource(id = R.drawable.ic_tag_love)
            }
            "DAILY" -> {
                painterResource(id = R.drawable.ic_tag_daily)
            }
            "IF" -> {
                painterResource(id = R.drawable.ic_tag_if)
            }
            else -> {
                painterResource(id = R.drawable.ic_tag_event)
            }
        }

        Image(
            painter = cardImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = tag,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.horizontal_padding),
                    vertical = 32.dp
                )
        )
    }
}

@Composable
fun BackCardFace(
    item: TopicItem,
    isBookmarked: Boolean,
    starter: TalkOrderItem? = null,
    onClickBookmark: ((Boolean) -> Unit)? = null,
    updateViewCnt: () -> Unit,
    onClickStarter: (() -> Unit)? = null,
    onClickShowDialog: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val cardImage = painterResource(id = R.drawable.card_back)

        Image(painter = cardImage, contentDescription = null, modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 8.dp)
        ) {
            Spacer(
                modifier = Modifier.height(2.dp)
            )
            Topic(item, isBookmarked, onClickBookmark)
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(White)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 5.dp)
                    .background(Gray03)
            )
            starter?.let {
                Starter(starter.rule ?: "") {
                    onClickStarter?.invoke()
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 5.dp)
                    .background(Gray03)
            )
            ShareBottom(onClickShareLink = {
                updateViewCnt()
                shareLink(context, item.shareLink + "&rule=${starter?.id}")
                logEvent(
                    AnalyticsConst.Event.CLICK_CARD_SHARE,
                    hashMapOf(AnalyticsConst.Key.TOPIC_ID to item.id.toString())
                )
            }, onClickScreenShot = {
                updateViewCnt()
                shareScreenShot(context) {
                    onClickShowDialog()
                }
                logEvent(
                    AnalyticsConst.Event.CLICK_CARD_DOWNLOAD,
                    hashMapOf(AnalyticsConst.Key.TOPIC_ID to item.id.toString())
                )
            })
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}

@Composable
fun Topic(
    item: TopicItem,
    isBookmarked: Boolean,
    onClickBookmark: ((Boolean) -> Unit)? = null
) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = stringResource(R.string.detail_topic),
                color = Gray05,
                style = TalkbbokkiTypography.b2_regular
            )
            onClickBookmark?.let {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp)
                        .padding(1.dp)
                        .clickable {
                            onClickBookmark(!isBookmarked)
                        },
                    painter = painterResource(
                        id = if (isBookmarked) R.drawable.ic_star_fill else R.drawable.ic_star_empty
                    ),
                    tint = if (isBookmarked) MainColor01 else Gray04,
                    contentDescription = null
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        Text(
            text = item.name,
            style = TalkbbokkiTypography.b1_bold,
            color = Black
        )
    }
}

@Composable
fun Starter(starter: String, onClickStarter: () -> Unit) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Row(modifier = Modifier.height(22.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(R.string.detail_starter),
                color = Gray05,
                style = TalkbbokkiTypography.b2_regular,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(1.dp)
                    .clickable {
                        onClickStarter()
                    }
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_refresh),
                tint = Gray05,
                contentDescription = null
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        Text(
            text = starter,
            style = TalkbbokkiTypography.b1_bold,
            color = Black,
            modifier = Modifier.defaultMinSize(minHeight = 56.dp)
        )
    }
}

@Composable
fun ShareBottom(
    onClickShareLink: () -> Unit,
    onClickScreenShot: () -> Unit
) {
    Row(modifier = Modifier.height(64.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { onClickShareLink() }
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Gray03)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { onClickScreenShot() }
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = null
            )
        }
    }
}

enum class CardFace {
    FRONT, BACK
}
