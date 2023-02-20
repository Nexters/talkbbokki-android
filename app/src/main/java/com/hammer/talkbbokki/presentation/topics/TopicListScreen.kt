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
import com.hammer.talkbbokki.presentation.showRewardedAd
import com.hammer.talkbbokki.ui.theme.Gray07
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun TopicListRoute(
    onClickToDetail: (id: String) -> Unit,
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
    onClickToDetail: (id: String) -> Unit,
    onClickToMain: () -> Unit,
    topicLevel: String,
    viewModel: TopicListViewModel
) {
    var selectedIdx by remember { mutableStateOf("0") }
    val topicList by viewModel.topicList.collectAsState()
    viewModel.getTodayViewCnt()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopicLevel.valueOf(topicLevel).backgroundColor)
    ) {
        TopicListHeader(onClickToMain, topicLevel)
        TopicList(onFocusedCardChange = { idx -> selectedIdx = idx }, viewModel)
        SelectBtn(
            isOpened = (selectedIdx.toInt() % 2 == 0),
            todayViewCnt = viewModel.todayViewCnt.value,
            onCardClicked = {
                viewModel.setTodayViewCnt()
                onClickToDetail(selectedIdx)
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
fun TopicList(onFocusedCardChange: (idx: String) -> Unit, viewModel: TopicListViewModel) {
    val listState = rememberLazyListState()
    val itemWidth =
        with(LocalDensity.current) { (dimensionResource(id = R.dimen.card_width)).toPx() }
    val currentIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val snappingLayout = remember(listState) { SnapLayoutInfoProvider(listState) }
    val snapFlingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val currentOffset = currentIndex.value + (offset.value / itemWidth)

    val cardList = (0..10).toList()

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
                itemsIndexed(cardList) { definiteIndex, _ ->
                    onFocusedCardChange(currentOffset.roundToInt().toString())
                    CardItem(definiteIndex, currentOffset, viewModel)
                }
            }
        }
    }
}

@Composable
fun SelectBtn(
    isOpened: Boolean = false,
    todayViewCnt: Int,
    onCardClicked: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    val openable by remember { mutableStateOf(false) }

    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        onClick = {
            /*if (todayViewCnt >= 10) {
                // TODO 10회 초과 다이얼로그 노출
            } else*/
            if (todayViewCnt >= 3) {
                showRewardedAd(context) {
                    onCardClicked()
                }
            } else {
                // 이미 열어본 카드 일 경우 처리git
                onCardClicked()
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
            text = if (isOpened)
                stringResource(R.string.list_re_pick_btn)
            else if (todayViewCnt >= 3)
                stringResource(R.string.list_ad_pick_btn)
            else
                stringResource(R.string.list_pick_btn),
            style = TalkbbokkiTypography.button_large, color = Color.White
        )
    }
}

@Composable
fun CardItem(definiteIndex: Int, currentOffset: Float, viewModel: TopicListViewModel) {
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
                    start = 1f.dp, stop = 1.9.dp, fraction = 1f - pageOffset.coerceIn(0f, 1.3f)
                ).let { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }

                // 중간으로 올수록 0도에 가까워짐. (카드 사이 각도 15도씩 틀어짐)
                lerp(
                    start = pageOffsetWithSign * 15f.dp, // -30, -15, 0, 15, 30 ...
                    stop = 0f.dp, fraction = 1f - pageOffset.coerceIn(0f, 1f)
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
            painter = cardImage, contentDescription = null, modifier = Modifier.fillMaxSize()
        )

        Box(modifier = Modifier.fillMaxSize()) {
            val tag = "LOVE"
            val tagImage: Painter = when (tag) {
                "LOVE" -> {
                    painterResource(id = R.drawable.ic_tag_love)
                }
                "DAILY" -> {
                    painterResource(id = R.drawable.ic_tag_daily)
                }
                else -> {
                    painterResource(id = R.drawable.ic_tag_if)
                }
            }
            var isCenter by remember { mutableStateOf(true) }
            isCenter = pageOffset < 0.5

            AnimatedVisibility(
                visible = isCenter, enter = fadeIn(), exit = fadeOut()
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

        if (pageOffset <= 0.5 && viewModel.todayViewCnt.value >= 3) {
            ShowLogic(Modifier.align(Alignment.TopCenter), definiteIndex % 2 == 0)
        }
    }
}

@Composable
fun ShowLogic(modifier: Modifier = Modifier, isOpened: Boolean = false) {
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
        } else {
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
