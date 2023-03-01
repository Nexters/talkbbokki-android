package com.hammer.talkbbokki.presentation.topics

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.showRewardedAd
import com.hammer.talkbbokki.ui.theme.Gray07
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White
import kotlin.math.absoluteValue

@Composable
fun TopicListRoute(
    onClickToDetail: (level: String, item: TopicItem) -> Unit,
    onClickToMain: () -> Unit,
    viewModel: TopicListViewModel = hiltViewModel(),
    topicLevel: String
) {
    TopicListScreen(
        onClickToDetail = onClickToDetail,
        onClickToMain = onClickToMain,
        topicLevel = topicLevel.toUpperCase(),
        viewModel = viewModel
    )
}

@Composable
fun TopicListScreen(
    onClickToDetail: (level: String, item: TopicItem) -> Unit,
    onClickToMain: () -> Unit,
    topicLevel: String,
    viewModel: TopicListViewModel
) {
    val list by viewModel.topicList.collectAsState()
    val todayViewCnt by viewModel.todayViewCnt.collectAsState()
    var selectedTopicItem by remember { mutableStateOf(TopicItem()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopicLevel.valueOf(topicLevel).backgroundColor)
    ) {
        TopicListHeader(onClickToMain, topicLevel)
        TopicList(
            cardList = list,
            onFocusedCardChange = { item -> selectedTopicItem = item },
            todayViewCnt
        )
        SelectBtn(
            isOpened = selectedTopicItem.isOpened,
            viewCountOver = todayViewCnt >= 3,
            onCardClicked = {
                selectedTopicItem.let { item ->
                    viewModel.setTodayViewCnt(id = item.id)
                    onClickToDetail(topicLevel, item)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TopicListHeader(onClickToMain: () -> Unit, topicLevel: String) {
    Column(
        modifier = Modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.vertical_padding),
                horizontal = dimensionResource(id = R.dimen.horizontal_padding)
            )
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .size(24.dp, 24.dp)
                .clickable { onClickToMain() }
        )
        Spacer(modifier = Modifier.height(24.dp))
        stringResource(id = TopicLevel.valueOf(topicLevel).title).split("\n")
            .forEachIndexed { index, s ->
                Text(
                    text = if (index == 0) s else s,
                    style = TalkbbokkiTypography.h2_bold,
                    color = if (index == 0) Color.Black else Color(0x80000000)
                )
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicList(
    cardList: List<TopicItem>,
    onFocusedCardChange: (item: TopicItem) -> Unit,
    todayViewCnt: Int
) {
    val listState = rememberLazyListState()
    val itemWidth =
        with(LocalDensity.current) { (dimensionResource(id = R.dimen.card_width)).toPx() }
    val currentIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val snappingLayout = remember(listState) { SnapLayoutInfoProvider(listState) }
    val snapFlingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val currentOffset = currentIndex.value + (offset.value / itemWidth)

    Column() {
        Spacer(modifier = Modifier.weight(1f))
        BoxWithConstraints(
            modifier = Modifier.weight(9f)
        ) {
            // 대화 주제 리스트
            LazyRow(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                flingBehavior = snapFlingBehavior,
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(
                    start = ((maxWidth - (dimensionResource(id = R.dimen.card_width))) / 2),
                    end = ((maxWidth - (dimensionResource(id = R.dimen.card_width))) / 2)
                )
            ) {
                itemsIndexed(cardList) { definiteIndex, item ->
                    CardItem(definiteIndex, currentOffset, item, todayViewCnt) {
                        onFocusedCardChange(it)
                    }
                }
            }
        }
    }
}

@Composable
fun SelectBtn(
    isOpened: Boolean = false,
    viewCountOver: Boolean = false,
    onCardClicked: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        onClick = {
            /*if (todayViewCnt >= 10) {
                // TODO 10회 초과 다이얼로그 노출
            } else */
            when {
                isOpened -> onCardClicked()
                viewCountOver -> {
                    showRewardedAd(context) {
                        onCardClicked()
                    }
                }
                else -> onCardClicked()
            }
        },
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.vertical_padding))
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
            .height(60.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = if (isOpened) {
                stringResource(R.string.list_re_pick_btn)
            } else if (viewCountOver) {
                stringResource(R.string.list_ad_pick_btn)
            } else {
                stringResource(R.string.list_pick_btn)
            },
            style = TalkbbokkiTypography.button_large,
            color = Color.White
        )
    }
}

@Composable
fun CardItem(
    definiteIndex: Int,
    currentOffset: Float,
    item: TopicItem,
    todayViewCnt: Int,
    currentItem: (TopicItem) -> Unit
) {
    val pageOffsetWithSign = definiteIndex - currentOffset
    val pageOffset = pageOffsetWithSign.absoluteValue
    Box(
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.card_width))
            .aspectRatio(0.65f)
            .background(Color.Transparent)
            .zIndex(5f - pageOffset)
            .graphicsLayer {
                // 중간으로 올수록 크게 보임
                lerp(
                    start = 1f.dp,
                    stop = 1.9.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1.3f)
                ).let { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }

                // 중간으로 올수록 0도에 가까워짐. (카드 사이 각도 15도씩 틀어짐)
                lerp(
                    start = pageOffsetWithSign * 15f.dp, // -30, -15, 0, 15, 30 ...
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { angle ->
                    rotationZ = angle
                }

                // 중간에서 멀어질수록 수직으로 내려가도록 조정.
                lerp(
                    start = pageOffset * 50f.dp,
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { yOffset ->
                    translationY = yOffset
                }

                // 카드가 겹치게 보이도록 중앙으로 모이도록 조정.
                lerp(
                    start = pageOffsetWithSign * pageOffset * (50f * -1).dp,
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { xOffset ->
                    translationX = xOffset
                }
            }
    ) {
        val cardImage: Painter = if (pageOffset > 1.5) {
            painterResource(id = R.drawable.bg_card_small)
        } else if (pageOffset > 0.5) {
            painterResource(id = R.drawable.bg_card_regular)
        } else {
            painterResource(id = R.drawable.bg_card_large)
        }

        Image(
            painter = cardImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        var isCenter by remember { mutableStateOf(true) }
        isCenter = pageOffset < 0.5

        Box(modifier = Modifier.fillMaxSize()) {
            val tag = item.tag
            val tagImage: Painter = when (tag) {
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

            AnimatedVisibility(
                visible = isCenter,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Image(
                    painter = tagImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                )
            }
        }

        if (isCenter) {
            ShowLogic(
                modifier = Modifier.align(Alignment.TopCenter),
                isOpened = item.isOpened,
                viewCountOver = todayViewCnt >= 3
            )
            currentItem(item)
        }
    }
}

@Composable
fun ShowLogic(
    modifier: Modifier = Modifier,
    isOpened: Boolean = false,
    viewCountOver: Boolean = false
) {
    Box(
        modifier = modifier.size(dimensionResource(id = R.dimen.card_tooltip_width), 40.dp)
    ) {
        if (isOpened) {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = stringResource(R.string.list_opened_card),
//                style = TalkbbokkiTypography.b2_bold,
                style = TalkbbokkiTypography.test,
                color = Color.White
            )
        } else if (viewCountOver) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(34.dp)
                    .background(Gray07, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = stringResource(R.string.list_more_card),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
//                    style = TalkbbokkiTypography.b3_regular,
                    style = TalkbbokkiTypography.test,
                    color = White,
                    textAlign = TextAlign.Center
                )
            }
            Image(
                modifier = Modifier
                    .padding(top = 34.dp)
                    .size(8.dp)
                    .align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.triangle),
                contentDescription = null
            )
        }
    }
}
